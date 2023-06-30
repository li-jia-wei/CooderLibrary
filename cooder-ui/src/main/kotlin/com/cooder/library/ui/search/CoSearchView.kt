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
import androidx.core.view.setPadding
import androidx.core.widget.addTextChangedListener
import com.cooder.library.ui.search.CoSearchView.SearchStatus.STATUS_HINT
import com.cooder.library.ui.search.CoSearchView.SearchStatus.STATUS_INPUT
import com.cooder.library.ui.search.CoSearchView.SearchStatus.STATUS_KEYWORD
import com.cooder.library.ui.view.IconFontButton
import com.cooder.library.ui.view.IconFontTextView

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/6/28 00:41
 *
 * 介绍：CoSearchView
 */
class CoSearchView @JvmOverloads constructor(
	context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
	
	private val searchAttr: AttrsParse.SearchAttr
	private val textAttr: AttrsParse.TextAttr
	private val navAttr: AttrsParse.NavAttr
	private val hintAttr: AttrsParse.HintAttr
	private val clearAttr: AttrsParse.ClearAttr
	private val confirmAttr: AttrsParse.ConfirmAttr
	private val keywordAttr: AttrsParse.KeywordAttr
	
	private var navView: IconFontButton? = null
	private var confirmView: IconFontButton? = null
	
	private var searchContainer: LinearLayout? = null
	private var editText: EditText? = null
	private var clearView: IconFontButton? = null
	private var keywordContainer: LinearLayout? = null
	private var keywordView: TextView? = null
	
	private var searchListener: (text: String) -> Unit = {}
	
	private var currentStatus = STATUS_HINT
	
	private var lastSearch = ""
	
	private val searchStatusViews = mutableMapOf<SearchStatus, MutableList<View>>()
	
	private companion object {
		private const val KEYWORD_ELLIPSIZE_START = 0
		private const val KEYWORD_ELLIPSIZE_MIDDLE = 1
		private const val KEYWORD_ELLIPSIZE_END = 2
	}
	
	private fun putSearchStatusView(status: SearchStatus, view: View) {
		searchStatusViews[status]?.add(view) ?: apply {
			searchStatusViews[status] = mutableListOf(view)
		}
	}
	
	enum class SearchStatus {
		STATUS_HINT, STATUS_INPUT, STATUS_KEYWORD
	}
	
	init {
		AttrsParse.parseAttrs(context, attrs, defStyleAttr).also {
			searchAttr = it.searchAttr
			textAttr = it.textAttr
			navAttr = it.navAttr
			hintAttr = it.hintAttr
			clearAttr = it.clearAttr
			confirmAttr = it.confirmAttr
			keywordAttr = it.keywordAttr
		}
		initNav()
		initSearch()
		initConfirm()
	}
	
	/**
	 * 设置左侧导航栏的点击事件
	 */
	fun setNavListener(listener: OnClickListener) {
		initNav()
		navView!!.setOnClickListener(listener)
	}
	
	/**
	 * 设置搜索的监听事件
	 */
	fun setSearchListener(listener: (String) -> Unit) {
		this.searchListener = listener
	}
	
	/**
	 * 设置历史记录
	 */
	fun setHistorySearch(content: String) {
		lastSearch = content
		editText!!.hint = content
	}
	
	/**
	 * 初始化导航按钮
	 */
	private fun initNav() {
		if (navAttr.enabled && navView == null) {
			navView = generateIconFontButton()
			navView!!.apply {
				this.setTextSize(TypedValue.COMPLEX_UNIT_PX, navAttr.iconSize * 2F)
				this.setTextColor(navAttr.color)
				this.setPadding(navAttr.padding, 0, navAttr.padding, 0)
				this.text = navAttr.icon
				this.id = View.generateViewId()
			}
			val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
			addView(navView!!, params)
		}
	}
	
	/**
	 * 初始化确认按钮
	 */
	private fun initConfirm() {
		if (confirmAttr.enabled && confirmView == null) {
			confirmView = generateIconFontButton()
			confirmView!!.apply {
				this.setTextColor(confirmAttr.color)
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
			}
			val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
			addView(confirmView!!, params)
		}
	}
	
	private fun generateIconFontButton(): IconFontButton {
		val button = IconFontButton(context)
		button.gravity = Gravity.CENTER
		return button
	}
	
	/**
	 * 初始化搜索框
	 */
	private fun initSearch() {
		ensureSearchContainer()
		initHint()
		initEditText()
		initClear()
		initKeyword()
	}
	
	private fun ensureSearchContainer() {
		if (searchContainer == null) {
			searchContainer = LinearLayout(context)
			searchContainer!!.apply {
				this.setBackgroundResource(searchAttr.background)
				this.gravity = if (hintAttr.gravity == 0) Gravity.CENTER else Gravity.CENTER_VERTICAL or Gravity.LEFT
				this.setOnClickListener {
					editText!!.setText(lastSearch)
					updateSearchStatus(STATUS_INPUT)
					clearView?.visibility = if (lastSearch.isNotEmpty()) View.VISIBLE else View.GONE
				}
			}
			val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
			params.topMargin = searchAttr.vertical
			params.bottomMargin = searchAttr.vertical
			if (!navAttr.enabled) params.leftMargin = searchAttr.horizontal
			if (!confirmAttr.enabled) params.rightMargin = searchAttr.horizontal
			params.weight = 1F
			addView(searchContainer!!, params)
		}
	}
	
	private fun initHint() {
		if (hintAttr.iconEnabled) {
			val hintIconView = IconFontTextView(context)
			hintIconView.apply {
				this.setTextSize(TypedValue.COMPLEX_UNIT_PX, hintAttr.iconSize * 1.4F)
				this.setTextColor(hintAttr.textColor)
				this.text = hintAttr.icon
				this.setPadding(hintAttr.padding, 0, 0, 0)
			}
			val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			searchContainer!!.addView(hintIconView, params)
			putSearchStatusView(STATUS_HINT, hintIconView)
		}
		val hintTextView = TextView(context)
		hintTextView.apply {
			this.setTextSize(TypedValue.COMPLEX_UNIT_PX, hintAttr.textSize.toFloat())
			this.setTextColor(hintAttr.textColor)
			this.text = hintAttr.text
			this.setPadding(hintAttr.padding, 0, hintAttr.padding, 0)
		}
		val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
		searchContainer!!.addView(hintTextView, params)
		putSearchStatusView(STATUS_HINT, hintTextView)
	}
	
	private fun initEditText() {
		if (editText == null) {
			editText = EditText(context)
			editText!!.apply {
				this.setBackgroundColor(Color.TRANSPARENT)
				this.setTextColor(textAttr.color)
				this.setTextSize(TypedValue.COMPLEX_UNIT_PX, textAttr.size.toFloat())
				this.setPadding(hintAttr.padding, 0, hintAttr.padding, 0)
				this.id = View.generateViewId()
				this.maxLines = 1
				this.filters = arrayOf(InputFilter.LengthFilter(searchAttr.maxLength))
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
					if (currentStatus == STATUS_INPUT) {
						val length = it?.length ?: 0
						clearView?.visibility = if (length == 0) View.GONE else View.VISIBLE
					} else {
						clearView?.visibility = View.GONE
					}
				}
			}
			val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
			params.weight = 1F
			searchContainer!!.addView(editText, params)
			putSearchStatusView(STATUS_INPUT, editText!!)
		}
	}
	
	private fun initClear() {
		if (clearAttr.enabled && clearView == null) {
			clearView = IconFontButton(context)
			clearView!!.apply {
				this.text = clearAttr.icon
				this.setTextSize(TypedValue.COMPLEX_UNIT_PX, clearAttr.size * 1.4F)
				this.setTextColor(clearAttr.color)
				this.setPadding(clearAttr.padding)
				this.visibility = View.GONE
				this.setOnClickListener {
					editText!!.setText("")
				}
			}
			val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
			searchContainer!!.addView(clearView!!, params)
			putSearchStatusView(STATUS_INPUT, clearView!!)
		}
	}
	
	private fun initKeyword() {
		if (keywordAttr.enabled && keywordView == null) {
			apply {
				keywordContainer = LinearLayout(context)
				keywordContainer!!.apply {
					this.gravity = Gravity.CENTER
					this.setBackgroundResource(keywordAttr.background)
					this.visibility = View.GONE
					this.setOnClickListener {
						editText!!.setText(lastSearch)
						updateSearchStatus(STATUS_INPUT)
						clearView?.visibility = if (lastSearch.isNotEmpty()) View.VISIBLE else View.GONE
					}
				}
				val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
				params.leftMargin = (keywordAttr.padding * 1.5).toInt()
				searchContainer!!.addView(keywordContainer!!, params)
			}
			apply {
				keywordView = TextView(context)
				keywordView!!.apply {
					this.setTextSize(TypedValue.COMPLEX_UNIT_PX, keywordAttr.textSize.toFloat())
					this.setTextColor(keywordAttr.textColor)
					this.isSingleLine = true
					this.maxWidth = keywordAttr.maxWidth
					this.ellipsize = when (keywordAttr.ellipsize) {
						KEYWORD_ELLIPSIZE_START -> TextUtils.TruncateAt.START
						KEYWORD_ELLIPSIZE_MIDDLE -> TextUtils.TruncateAt.MIDDLE
						KEYWORD_ELLIPSIZE_END -> TextUtils.TruncateAt.END
						else -> TextUtils.TruncateAt.MIDDLE
					}
					this.setPadding((keywordAttr.padding * 1.5).toInt(), 0, 0, 0)
				}
				val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
				keywordContainer!!.addView(keywordView, params)
			}
			apply {
				val closeKeywordView = IconFontButton(context)
				closeKeywordView.setTextSize(TypedValue.COMPLEX_UNIT_PX, keywordAttr.iconSize * 1.4F)
				closeKeywordView.setTextColor(keywordAttr.textColor)
				closeKeywordView.text = keywordAttr.icon
				closeKeywordView.setPadding(keywordAttr.padding)
				closeKeywordView.setOnClickListener {
					editText!!.setText("")
					updateSearchStatus(STATUS_INPUT)
					editText!!.hint = lastSearch
					clearView?.visibility = View.GONE
				}
				val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
				keywordContainer!!.addView(closeKeywordView, params)
			}
			putSearchStatusView(STATUS_KEYWORD, keywordContainer!!)
		}
	}
	
	private fun searchCallback() {
		val search = formatText(editText!!.text.toString())
		if ((search.isBlank() && lastSearch.isBlank()) || currentStatus != STATUS_INPUT) return
		editText!!.clearFocus()
		hideSoftKeyboard()
		if (search.isNotBlank() && search != lastSearch) {
			lastSearch = search
		} else {
			editText!!.setText(lastSearch)
		}
		updateSearchStatus(STATUS_KEYWORD)
		searchListener.invoke(lastSearch)
		editText!!.hint = lastSearch
	}
	
	private fun hideSoftKeyboard() {
		val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		manager.hideSoftInputFromWindow(editText!!.windowToken, 0)
	}
	
	private fun showSoftKeyboard() {
		val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		manager.showSoftInput(editText!!, InputMethodManager.SHOW_IMPLICIT)
	}
	
	private fun formatText(text: String): String {
		return text.trim().replace("\\s+".toRegex(), " ")
	}
	
	private fun updateSearchStatus(status: SearchStatus) {
		searchContainer!!.gravity = Gravity.CENTER_VERTICAL or Gravity.LEFT
		var realStatus = status
		if (status == STATUS_KEYWORD && !keywordAttr.enabled) {
			realStatus = STATUS_INPUT
		}
		if (currentStatus != realStatus) {
			searchStatusViews.forEach { (s, vs) ->
				if (status == s) {
					vs.forEach {
						it.visibility = View.VISIBLE
					}
				} else {
					vs.forEach {
						it.visibility = View.GONE
					}
				}
			}
			if (status == STATUS_INPUT) {
				editText!!.requestFocus()
				showSoftKeyboard()
			} else if (status == STATUS_KEYWORD) {
				if (lastSearch.isNotBlank()) {
					keywordView!!.text = lastSearch
					keywordContainer!!.visibility = View.VISIBLE
				}
			}
			currentStatus = status
		}
	}
}