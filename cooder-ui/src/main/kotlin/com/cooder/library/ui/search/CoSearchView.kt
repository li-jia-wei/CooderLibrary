package com.cooder.library.ui.search

import android.content.Context
import android.graphics.Color
import android.text.InputFilter
import android.text.InputType
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.core.view.setPadding
import androidx.core.widget.addTextChangedListener
import com.cooder.library.library.util.CoMainHandler
import com.cooder.library.library.util.expends.dpInt
import com.cooder.library.ui.search.CoSearchView.Status.HINT
import com.cooder.library.ui.search.CoSearchView.Status.INPUT
import com.cooder.library.ui.search.CoSearchView.Status.KEYWORD
import com.cooder.library.ui.view.IconFontButton
import com.cooder.library.ui.view.IconFontTextView

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/1 10:56
 *
 * 介绍：搜索框组件
 */
class CoSearchView @JvmOverloads constructor(
	context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
	
	private val attr = AttrParse.parseAttr(context, attrs, defStyleAttr)
	private val searchAttr = attr.searchAttr
	private val textAttr = attr.textAttr
	private val navAttr = attr.navAttr
	private val hintAttr = attr.hintAttr
	private val clearAttr = attr.clearAttr
	private val confirmAttr = attr.confirmAttr
	private val keyWordAttr = attr.keyWordAttr
	
	private var navListener: (() -> Unit)? = null
	private var searchListener: ((keyWord: String) -> Unit)? = null
	private var searchContentChangeListener: ((keyWord: String) -> Unit)? = null
	
	private var canBackToHint: Boolean = false
	private var lastSearchContent = ""
	private var lastChangeContent = ""
	
	private var confirmView: IconFontButton? = null
	private lateinit var searchContainer: LinearLayout
	private lateinit var editText: EditText
	private var clearView: IconFontButton? = null
	private var keyWordContainer: LinearLayout? = null
	private var keyWordView: TextView? = null
	private var keyWordCloseView: IconFontButton? = null
	
	private val statusViews = mutableMapOf<View, Array<Status>>()
	private var currentStatus = HINT
	
	private val changeRunnable = Runnable {
		val content = this.getKeyWord()
		if (content != lastChangeContent) {
			lastChangeContent = content
			searchContentChangeListener?.invoke(content)
		}
	}
	
	private companion object {
		private val CONFIRM_NOT_CLICKABLE_COLOR = "#999999".toColorInt()
	}
	
	enum class Status {
		HINT, INPUT, KEYWORD
	}
	
	init {
		initNav()
		initSearchContainer()
		initHint()
		initEditText()
		initClear()
		initKeyword()
		initConfirm()
	}
	
	/**
	 * 设置点击左侧导航按钮事件
	 * @param canBackToHint 先返回到提示状态，再执行navListener事件
	 */
	@JvmOverloads
	fun setNavListener(canBackToHint: Boolean = false, listener: () -> Unit) {
		this.navListener = listener
		this.canBackToHint = canBackToHint
	}
	
	/**
	 * 设置搜索的内容监听事件
	 */
	fun setSearchListener(callback: (keyWord: String) -> Unit) {
		this.searchListener = callback
	}
	
	/**
	 * 输入框文本修改事件，默认300ms的延迟，如果快速输入，只会在最后一个超过300ms的时候执行
	 */
	fun setSearchContentChangeListener(listener: (keyWord: String) -> Unit) {
		this.searchContentChangeListener = listener
	}
	
	/**
	 * 关闭提示，直接进入输入状态
	 */
	fun closeHint() {
		updateStatus(INPUT)
	}
	
	/**
	 * 设置顶部内边距
	 */
	fun setTopPadding(topPadding: Int) {
		val params = layoutParams
		params.height += topPadding.dpInt
		this.layoutParams = params
		this.setPadding(0, topPadding.dpInt, 0, 0)
	}
	
	/**
	 * 设置上一次搜索内容
	 */
	fun setHistorySearchContent(keyWord: String) {
		if (keyWord.isNotEmpty()) {
			this.lastSearchContent = keyWord
			editText.hint = keyWord
			confirmView?.setTextColor(confirmAttr.color)
		}
	}
	
	/**
	 * 设置关键字
	 */
	fun setKeyWord(keyWord: String) {
		hideSoftKeyboard()
		lastSearchContent = keyWord
		lastChangeContent = keyWord
		editText.setText(keyWord)
		editText.hint = keyWord
		keyWordView?.text = keyWord
		clearView?.visibility = View.GONE
		updateStatus(KEYWORD)
	}
	
	/**
	 * 设置关键字是否能被点击
	 */
	fun setKeyWordCloseClickable(clickable: Boolean) {
		keyWordCloseView?.isClickable = clickable
	}
	
	/**
	 * 初始化左侧导航栏
	 */
	private fun initNav() {
		if (navAttr.enabled && searchAttr.enabled) {
			val navView = IconFontButton(context)
			navView.apply {
				this.gravity = Gravity.CENTER
				this.setTextSize(TypedValue.COMPLEX_UNIT_PX, navAttr.iconSize * 2F)
				this.setTextColor(navAttr.color)
				this.setPadding(navAttr.padding, 0, navAttr.padding, 0)
				this.text = navAttr.icon
				navAttr.typeface?.also { this.setTypeface(it) }
				this.setOnClickListener {
					if (!canBackToHint || currentStatus == HINT) {
						navListener?.invoke()
					} else {
						updateStatus(HINT)
					}
				}
				val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
				this@CoSearchView.addView(this, params)
			}
		}
	}
	
	/**
	 * 初始化确认框
	 */
	private fun initConfirm() {
		if (confirmAttr.enabled && searchAttr.enabled) {
			confirmView = IconFontButton(context)
			confirmView!!.apply {
				this.gravity = Gravity.CENTER
				this.setTextColor(CONFIRM_NOT_CLICKABLE_COLOR)
				if (confirmAttr.icon != null) {
					this.text = confirmAttr.icon
					this.setTextSize(TypedValue.COMPLEX_UNIT_PX, confirmAttr.size * 2F)
					this.setPadding(confirmAttr.padding, 0, confirmAttr.padding, 0)
				} else {
					this.text = confirmAttr.text
					this.setTextSize(TypedValue.COMPLEX_UNIT_PX, confirmAttr.size.toFloat())
					this.setPadding((confirmAttr.padding * 1.5).toInt(), 0, (confirmAttr.padding * 1.5).toInt(), 0)
				}
				this.setOnClickListener {
					searchCallback()
				}
				val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
				this@CoSearchView.addView(this, params)
			}
		}
	}
	
	/**
	 * 初始化搜索框
	 */
	private fun initSearchContainer() {
		searchContainer = LinearLayout(context)
		searchContainer.apply {
			this.setBackgroundResource(searchAttr.background)
			this.gravity = if (hintAttr.gravity == 0) Gravity.CENTER else Gravity.CENTER_VERTICAL or Gravity.LEFT
			if (searchAttr.enabled) {
				this.setOnClickListener {
					updateStatus(INPUT)
					val content = this@CoSearchView.getKeyWord()
					clearView?.visibility = if (lastSearchContent.isEmpty() && content.isEmpty()) View.GONE else View.VISIBLE
					editText.setText(lastSearchContent)
					editText.setSelection(editText.length())
					searchContentChangeListener?.invoke(content)
				}
			}
			this.orientation = HORIZONTAL
			val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
			params.topMargin = searchAttr.vertical
			params.bottomMargin = searchAttr.vertical
			if (!navAttr.enabled || !searchAttr.enabled) params.leftMargin = searchAttr.horizontal
			if (!confirmAttr.enabled || !searchAttr.enabled) params.rightMargin = searchAttr.horizontal
			params.weight = 1F
			this@CoSearchView.addView(this, params)
		}
	}
	
	/**
	 * 初始化提示框
	 */
	private fun initHint() {
		if (hintAttr.iconEnabled || searchAttr.enabled) {
			val hintIconView = IconFontTextView(context)
			hintIconView.apply {
				this.setTextSize(TypedValue.COMPLEX_UNIT_PX, hintAttr.iconSize * 1.4F)
				this.setTextColor(hintAttr.textColor)
				this.text = hintAttr.icon
				this.setPadding(hintAttr.padding, 0, 0, 0)
				val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
				searchContainer.addView(this, params)
				statusViews[this] = arrayOf(HINT, INPUT, KEYWORD)
			}
		}
		val hintTextView = TextView(context)
		hintTextView.apply {
			this.setTextSize(TypedValue.COMPLEX_UNIT_PX, hintAttr.textSize.toFloat())
			this.setTextColor(hintAttr.textColor)
			this.text = hintAttr.text
			this.setPadding(hintAttr.padding, 0, hintAttr.padding, 0)
			val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			searchContainer.addView(this, params)
			statusViews[this] = arrayOf(HINT)
		}
	}
	
	private fun initEditText() {
		if (searchAttr.enabled) {
			editText = EditText(context)
			editText.apply {
				this.setBackgroundColor(Color.TRANSPARENT)
				this.setTextColor(textAttr.color)
				this.setTextSize(TypedValue.COMPLEX_UNIT_PX, textAttr.size.toFloat())
				this.setPadding(hintAttr.padding, 0, hintAttr.padding, 0)
				this.isSingleLine = true
				this.hint = hintAttr.text
				this.filters = arrayOf(InputFilter.LengthFilter(textAttr.maxLength))
				this.inputType = InputType.TYPE_CLASS_TEXT
				this.imeOptions = EditorInfo.IME_ACTION_SEARCH
				this.setOnEditorActionListener { _, actionId, _ ->
					if (actionId == EditorInfo.IME_ACTION_SEARCH) {
						searchCallback()
						true
					} else false
				}
				this.visibility = View.GONE
				this.addTextChangedListener {
					if (it == null) return@addTextChangedListener
					val content = this@CoSearchView.getKeyWord()
					confirmView?.setTextColor(if (content.isEmpty() && lastSearchContent.isEmpty()) CONFIRM_NOT_CLICKABLE_COLOR else confirmAttr.color)
					clearView?.visibility = if (currentStatus == INPUT && it.isNotEmpty()) View.VISIBLE else View.GONE
					CoMainHandler.remove(changeRunnable)
					if (it.isEmpty()) {
						CoMainHandler.post(changeRunnable)
					} else {
						CoMainHandler.postDelay(searchAttr.changeInterval, changeRunnable)
					}
				}
				val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
				params.weight = 1F
				searchContainer.addView(this, params)
				statusViews[this] = arrayOf(INPUT)
			}
		}
	}
	
	private fun initClear() {
		if (clearAttr.enabled && searchAttr.enabled) {
			clearView = IconFontButton(context)
			clearView!!.apply {
				this.text = clearAttr.icon
				this.setTextSize(TypedValue.COMPLEX_UNIT_PX, clearAttr.size * 1.4F)
				this.setTextColor(clearAttr.color)
				this.setPadding(clearAttr.padding)
				this.visibility = View.GONE
				this.setOnClickListener {
					editText.setText("")
				}
				val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
				searchContainer.addView(this, params)
			}
		}
	}
	
	private fun initKeyword() {
		if (keyWordAttr.enabled && searchAttr.enabled) {
			keyWordContainer = LinearLayout(context)
			keyWordContainer!!.apply {
				this.gravity = Gravity.CENTER
				this.setBackgroundResource(keyWordAttr.background)
				this.visibility = View.GONE
				this.setOnClickListener {
					editText.setText(lastSearchContent)
					clearView?.visibility = if (lastSearchContent.isNotEmpty()) View.VISIBLE else View.GONE
					updateStatus(INPUT)
				}
				val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
				params.leftMargin = (keyWordAttr.padding * 1.5).toInt()
				params.rightMargin = (keyWordAttr.padding * 1.5).toInt()
				searchContainer.addView(this, params)
				statusViews[this] = arrayOf(KEYWORD)
			}
			
			keyWordView = TextView(context)
			keyWordView!!.apply {
				this.setTextSize(TypedValue.COMPLEX_UNIT_PX, keyWordAttr.textSize.toFloat())
				this.setTextColor(keyWordAttr.textColor)
				this.isSingleLine = true
				this.maxWidth = keyWordAttr.maxWidth
				this.ellipsize = when (keyWordAttr.ellipsize) {
					0 -> TextUtils.TruncateAt.START
					1 -> TextUtils.TruncateAt.MIDDLE
					else -> TextUtils.TruncateAt.END
				}
				this.setPadding((keyWordAttr.padding * 1.5).toInt(), 0, 0, 0)
				val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
				keyWordContainer!!.addView(this, params)
			}
			
			keyWordCloseView = IconFontButton(context)
			keyWordCloseView!!.apply {
				this.setTextSize(TypedValue.COMPLEX_UNIT_PX, keyWordAttr.iconSize * 1.4F)
				this.setTextColor(keyWordAttr.textColor)
				this.text = keyWordAttr.icon
				this.setPadding(keyWordAttr.padding)
				this.setOnClickListener {
					editText.setText("")
					updateStatus(INPUT)
					editText.hint = lastSearchContent
					clearView?.visibility = View.GONE
				}
				val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
				keyWordContainer!!.addView(this, params)
			}
		}
	}
	
	private fun searchCallback() {
		val keyWord = this.getKeyWord()
		if ((keyWord.isEmpty() && lastSearchContent.isEmpty()) || currentStatus != INPUT) {
			return
		}
		hideSoftKeyboard()
		if (keyWord.isNotEmpty()) {
			lastSearchContent = keyWord
			lastChangeContent = keyWord
		}
		keyWordView?.text = lastSearchContent
		editText.hint = lastSearchContent
		updateStatus(KEYWORD)
		clearView?.visibility = View.GONE
		searchListener?.invoke(lastSearchContent)
	}
	
	/**
	 * 更新UI显示与隐藏
	 */
	private fun updateStatus(status: Status) {
		if (status == HINT && hintAttr.gravity == 0) {
			searchContainer.gravity = Gravity.CENTER
		} else {
			searchContainer.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
		}
		var realStatus = status
		if (status == KEYWORD && !keyWordAttr.enabled) {
			realStatus = INPUT
		}
		if (realStatus != currentStatus) {
			statusViews.forEach { (view, statuses) ->
				view.visibility = if (realStatus in statuses) View.VISIBLE else View.GONE
			}
			if (realStatus == INPUT) {
				showSoftKeyboard()
			} else if (status == KEYWORD) {
				if (lastSearchContent.isEmpty()) {
					keyWordContainer?.visibility = View.GONE
				}
			}
			currentStatus = realStatus
		}
	}
	
	/**
	 * 格式化
	 */
	private fun getKeyWord(): String {
		val content = editText.text.toString()
		if (content.isNotBlank()) {
			return content.trim().replace("\\s+".toRegex(), " ")
		}
		return ""
	}
	
	/**
	 * 隐藏软键盘
	 */
	private fun hideSoftKeyboard() {
		editText.clearFocus()
		val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		manager.hideSoftInputFromWindow(editText.windowToken, 0)
	}
	
	/**
	 * 显示软键盘
	 */
	private fun showSoftKeyboard() {
		editText.requestFocus()
		val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		manager.showSoftInput(editText, InputMethodManager.HIDE_NOT_ALWAYS)
	}
}