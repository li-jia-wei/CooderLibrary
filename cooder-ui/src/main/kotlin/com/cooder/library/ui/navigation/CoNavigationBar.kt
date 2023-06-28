package com.cooder.library.ui.navigation

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatButton
import com.cooder.library.library.util.expends.dpInt
import com.cooder.library.ui.view.IconFontButton
import com.cooder.library.ui.view.IconFontTextView

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/6/25 10:42
 *
 * 介绍：导航栏组件
 */
class CoNavigationBar @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
	
	private val navAttrs = AttrsParse.parseNavAttrs(context, attrs, defStyleAttr)
	
	// 左右按钮
	private val leftViewList = mutableListOf<View>()
	private val rightViewList = mutableListOf<View>()
	private var leftLastViewId = View.NO_ID
	private var rightLastViewId = View.NO_ID
	
	private var navButton: IconFontButton? = null
	
	// 主副标题
	private var titleView: IconFontTextView? = null
	private var subTitleView: IconFontTextView? = null
	private var titleContainer: LinearLayout? = null
	
	init {
		if (!navAttrs.titleText.isNullOrBlank()) {
			setTitle(navAttrs.titleText)
		}
		if (!navAttrs.subTitleText.isNullOrBlank()) {
			setSubTitle(navAttrs.subTitleText)
		}
		setBackgroundColor(Color.WHITE)
		if (navAttrs.underlineEnabled) {
			this.translationZ = navAttrs.underlineHeight
		} else {
			this.translationZ = 0F
		}
	}
	
	fun setTopPadding(topPadding: Int) {
		val params = layoutParams
		params.height = params.height + topPadding.dpInt
		this.layoutParams = params
		this.setPadding(0, topPadding.dpInt, 0, 0)
	}
	
	fun setNavigationListener(listener: OnClickListener) {
		if (navButton == null && !navAttrs.navIcon.isNullOrBlank()) {
			navButton = addLeftIconButton(navAttrs.navIcon)
		}
		navButton?.setOnClickListener(listener)
	}
	
	/**
	 * 添加左侧图标按钮
	 */
	fun addLeftIconButton(@StringRes iconTextResId: Int, @ColorRes colorResId: Int = -1): IconFontButton {
		return addLeftButton(context.getString(iconTextResId), colorResId, true) as IconFontButton
	}
	
	/**
	 * 添加左侧图标按钮
	 */
	fun addLeftIconButton(text: String, @ColorRes colorResId: Int = -1): IconFontButton {
		return addLeftButton(text, colorResId, true) as IconFontButton
	}
	
	/**
	 * 添加左侧文本按钮
	 */
	fun addLeftTextButton(@StringRes textResId: Int, @ColorRes colorResId: Int = -1): Button {
		return addLeftButton(context.getString(textResId), colorResId, false)
	}
	
	/**
	 * 添加左侧文本按钮
	 */
	@JvmOverloads
	fun addLeftTextButton(text: String, @ColorRes colorResId: Int = -1): Button {
		return addLeftButton(text, colorResId, false)
	}
	
	/**
	 * 添加左侧按钮
	 */
	private fun addLeftButton(text: String, colorResId: Int, isIcon: Boolean): Button {
		val button = generateTextButton(isIcon, colorResId)
		button.text = text
		if (leftViewList.isEmpty()) {
			button.setPadding(navAttrs.elementPadding * 2, 0, navAttrs.elementPadding, 0)
		} else {
			button.setPadding(navAttrs.elementPadding, 0, navAttrs.elementPadding, 0)
		}
		val params = generateTextButtonLayoutParams()
		addLeftView(button, params)
		return button
	}
	
	private fun addLeftView(view: View, params: LayoutParams) {
		val viewId = view.id
		if (leftLastViewId == View.NO_ID) {
			params.addRule(ALIGN_PARENT_LEFT, viewId)
		} else {
			params.addRule(RIGHT_OF, leftLastViewId)
		}
		leftLastViewId = viewId
		params.alignWithParent = true
		leftViewList += view
		addView(view, params)
	}
	
	/**
	 * 添加右侧文本按钮
	 */
	@JvmOverloads
	fun addRightTextButton(@StringRes textResId: Int, @ColorRes colorResId: Int = -1): Button {
		return addRightButton(context.getString(textResId), colorResId, false)
	}
	
	/**
	 * 添加右侧文本按钮
	 */
	@JvmOverloads
	fun addRightTextButton(text: String, @ColorRes colorResId: Int = -1): Button {
		return addRightButton(text, colorResId, false)
	}
	
	/**
	 * 添加右侧图标按钮
	 */
	@JvmOverloads
	fun addRightIconButton(@StringRes iconTextResId: Int, @ColorRes colorResId: Int = -1): IconFontButton {
		return addRightButton(context.getString(iconTextResId), colorResId, true) as IconFontButton
	}
	
	/**
	 * 添加右侧图标按钮
	 */
	@JvmOverloads
	fun addRightIconButton(iconText: String, @ColorRes colorResId: Int = -1): IconFontButton {
		return addRightButton(iconText, colorResId, true) as IconFontButton
	}
	
	/**
	 * 添加右侧按钮
	 */
	private fun addRightButton(text: String, colorResId: Int, isIcon: Boolean): Button {
		val button = generateTextButton(isIcon, colorResId)
		button.text = text
		if (rightViewList.isEmpty()) {
			button.setPadding(navAttrs.elementPadding, 0, navAttrs.elementPadding * 2, 0)
		} else {
			button.setPadding(navAttrs.elementPadding, 0, navAttrs.elementPadding, 0)
		}
		val params = generateTextButtonLayoutParams()
		addRightView(button, params)
		return button
	}
	
	private fun addRightView(view: View, params: LayoutParams) {
		val viewId = view.id
		if (rightLastViewId == View.NO_ID) {
			params.addRule(ALIGN_PARENT_RIGHT, viewId)
		} else {
			params.addRule(LEFT_OF, rightLastViewId)
		}
		rightLastViewId = viewId
		params.alignWithParent = true
		rightViewList += view
		addView(view, params)
	}
	
	fun setTitle(@StringRes resId: Int) {
		setTitle(context.getString(resId))
	}
	
	fun setTitle(title: String) {
		ensureTitleView()
		this.titleView!!.let {
			it.text = title
			it.visibility = if (title.isNotBlank()) View.VISIBLE else View.GONE
		}
	}
	
	fun setSubTitle(@StringRes resId: Int) {
		setSubTitle(context.getString(resId))
	}
	
	fun setSubTitle(subTitle: String) {
		ensureSubTitleView()
		updateTitleViewStyle()
		this.subTitleView!!.let {
			it.text = subTitle
			it.visibility = if (subTitle.isNotBlank()) View.VISIBLE else View.GONE
		}
	}
	
	private fun ensureTitleView() {
		if (titleView == null) {
			titleView = IconFontTextView(context, null)
			titleView!!.also {
				it.gravity = Gravity.CENTER
				it.isSingleLine = true
				it.ellipsize = TextUtils.TruncateAt.END
				it.setTextColor(navAttrs.titleTextColor)
				ensureTitleContainer()
				updateTitleViewStyle()
				titleContainer!!.addView(it, 0)
			}
		}
	}
	
	private fun updateTitleViewStyle() {
		if (titleView != null) {
			if (subTitleView == null || subTitleView!!.visibility == View.GONE) {
				titleView!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, navAttrs.titleTextSize)
				titleView!!.typeface = Typeface.DEFAULT_BOLD
			} else {
				titleView!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, navAttrs.titleTextSizeWithSub)
				titleView!!.typeface = Typeface.DEFAULT
			}
		}
	}
	
	private fun ensureSubTitleView() {
		if (subTitleView == null) {
			subTitleView = IconFontTextView(context)
			subTitleView!!.also {
				it.gravity = Gravity.CENTER
				it.isSingleLine = true
				it.ellipsize = TextUtils.TruncateAt.END
				it.setTextColor(navAttrs.titleTextColor)
				ensureTitleContainer()
				titleContainer!!.addView(it)
			}
		}
	}
	
	private fun ensureTitleContainer() {
		if (titleContainer == null) {
			titleContainer = LinearLayout(context)
			titleContainer!!.also {
				it.orientation = LinearLayout.VERTICAL
				it.gravity = Gravity.CENTER
				val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
				params.addRule(CENTER_HORIZONTAL)
				params.addRule(ALIGN_PARENT_BOTTOM)
				addView(it, params)
			}
		}
	}
	
	private fun generateTextButtonLayoutParams(): LayoutParams {
		return LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
	}
	
	private fun generateTextButton(isIcon: Boolean, colorResId: Int): Button {
		val button = if (isIcon) IconFontButton(context) else AppCompatButton(context)
		button.setBackgroundResource(0)
		button.minWidth = 0
		button.minimumWidth = 0
		button.minHeight = 0
		button.minHeight = 0
		button.setTextSize(TypedValue.COMPLEX_UNIT_PX, if (isIcon) navAttrs.btnTextSize * 2 else navAttrs.btnTextSize)
		button.setTextColor(if (colorResId == -1) navAttrs.btnTextColor else context.getColor(colorResId))
		button.gravity = Gravity.CENTER
		button.id = View.generateViewId()
		return button
	}
	
	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec)
		if (titleContainer != null) {
			val titleContainerWidth = titleContainer!!.measuredWidth
			val remainingSpace = calcRemainingSpace()
			if (remainingSpace < titleContainerWidth) {
				val newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(remainingSpace, MeasureSpec.EXACTLY)
				titleContainer!!.measure(newWidthMeasureSpec, heightMeasureSpec)
			}
		}
	}
	
	private fun getLeftUseSpace(): Int {
		var leftUseSpace = paddingLeft
		leftViewList.forEach {
			leftUseSpace += it.measuredWidth
		}
		return leftUseSpace
	}
	
	private fun getRightUseSpace(): Int {
		var rightUseSpace = paddingRight
		rightViewList.forEach {
			rightUseSpace += it.measuredWidth
		}
		return rightUseSpace
	}
	
	private fun calcRemainingSpace(): Int {
		val leftUseSpace = getLeftUseSpace()
		val rightUseSpace = getRightUseSpace()
		return measuredWidth - Math.max(leftUseSpace, rightUseSpace) * 2
	}
}