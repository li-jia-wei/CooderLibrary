package com.cooder.cooder.ui.navigation

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
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.cooder.cooder.library.util.expends.dp
import com.cooder.cooder.library.util.expends.dpInt
import com.cooder.cooder.library.util.expends.spInt
import com.cooder.cooder.ui.R
import com.cooder.cooder.ui.view.IconFontButton
import com.cooder.cooder.ui.view.IconFontTextView

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

    private val navAttrs = parseNavAttrs(attrs, defStyleAttr)

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
        this.translationZ = TRANSLATION_Z.dp
    }

    private companion object {
        private const val BTN_TEXT_SIZE = 16
        private const val TITLE_TEXT_SIZE = 18
        private const val TITLE_TEXT_SIZE_WITH_SUB = 17
        private const val SUB_TITLE_TEXT_SIZE = 11
        private const val ELEMENT_PADDING = 8
        private const val TRANSLATION_Z = 3
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
    fun addLeftIconButton(@StringRes resId: Int): IconFontButton {
        return addLeftButton(context.getString(resId), true) as IconFontButton
    }

    /**
     * 添加左侧图标按钮
     */
    fun addLeftIconButton(text: String): IconFontButton {
        return addLeftButton(text, true) as IconFontButton
    }

    /**
     * 添加左侧文本按钮
     */
    fun addLeftTextButton(@StringRes resId: Int): Button {
        return addLeftButton(context.getString(resId), false)
    }

    /**
     * 添加左侧文本按钮
     */
    fun addLeftTextButton(text: String): Button {
        return addLeftButton(text, false)
    }

    /**
     * 添加左侧按钮
     */
    private fun addLeftButton(text: String, isIcon: Boolean): Button {
        val button = generateTextButton(isIcon)
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
    fun addRightTextButton(@StringRes resId: Int): Button {
        return addRightButton(context.getString(resId), false)
    }

    /**
     * 添加右侧文本按钮
     */
    fun addRightTextButton(text: String): Button {
        return addRightButton(text, false)
    }

    /**
     * 添加右侧图标按钮
     */
    fun addRightIconButton(resId: Int): IconFontButton {
        return addRightButton(context.getString(resId), true) as IconFontButton
    }

    /**
     * 添加右侧图标按钮
     */
    fun addRightIconButton(text: String): IconFontButton {
        return addRightButton(text, true) as IconFontButton
    }

    /**
     * 添加右侧按钮
     */
    private fun addRightButton(text: String, isIcon: Boolean): Button {
        val button = generateTextButton(isIcon)
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
                params.addRule(CENTER_IN_PARENT)
                addView(it, params)
            }
        }
    }

    private fun generateTextButtonLayoutParams(): LayoutParams {
        return LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
    }

    private fun generateTextButton(isIcon: Boolean): Button {
        val button = if (isIcon) IconFontButton(context) else Button(context)
        button.setBackgroundResource(0)
        button.minWidth = 0
        button.minimumWidth = 0
        button.minHeight = 0
        button.minHeight = 0
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, if (isIcon) navAttrs.btnTextSize * 2 else navAttrs.btnTextSize)
        button.setTextColor(navAttrs.btnTextColor)
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

    /**
     * 解析参数
     */
    private fun parseNavAttrs(attrs: AttributeSet?, defStyleAttr: Int): NavAttrs {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.navigationStyle, value, true)
        val defStyleRes = if (value.resourceId != 0) value.resourceId else R.style.CoNavigationBarStyle
        val array = context.obtainStyledAttributes(attrs, R.styleable.CoNavigationBar, defStyleAttr, defStyleRes)

        val navIcon = array.getString(R.styleable.CoNavigationBar_navIcon) ?: context.getString(R.string.nav_icon)

        val btnTextSize = array.getDimensionPixelOffset(R.styleable.CoNavigationBar_btnTextSize, BTN_TEXT_SIZE.dpInt).toFloat()
        val btnTextColor = array.getColor(R.styleable.CoNavigationBar_btnTextColor, context.getColor(R.color.nav_btn_text_color))

        val titleText = array.getString(R.styleable.CoNavigationBar_titleText)
        val titleTextSize = array.getDimensionPixelOffset(R.styleable.CoNavigationBar_titleTextSize, TITLE_TEXT_SIZE.spInt).toFloat()
        val titleTextSizeWithSub = array.getDimensionPixelOffset(R.styleable.CoNavigationBar_titleTextSizeWithSub, TITLE_TEXT_SIZE_WITH_SUB.spInt).toFloat()
        val titleTextColor = array.getColor(R.styleable.CoNavigationBar_titleTextColor, context.getColor(R.color.nav_title_text_color))

        val subTitleText = array.getString(R.styleable.CoNavigationBar_subTitleText)
        val subTitleTextSize = array.getDimensionPixelOffset(R.styleable.CoNavigationBar_subTitleTextSize, SUB_TITLE_TEXT_SIZE.dpInt).toFloat()
        val subTitleTextColor = array.getColor(R.styleable.CoNavigationBar_subTitleTextColor, context.getColor(R.color.nav_sub_title_text_color))

        val elementPadding = array.getDimensionPixelOffset(R.styleable.CoNavigationBar_elementPadding, ELEMENT_PADDING.dpInt)
        array.recycle()
        return NavAttrs(
            navIcon,
            btnTextSize,
            btnTextColor,
            titleText,
            titleTextSize,
            titleTextSizeWithSub,
            titleTextColor,
            subTitleText,
            subTitleTextSize,
            subTitleTextColor,
            elementPadding
        )
    }

    data class NavAttrs(
        val navIcon: String?,
        val btnTextSize: Float,
        @ColorInt val btnTextColor: Int,
        val titleText: String?,
        val titleTextSize: Float,
        val titleTextSizeWithSub: Float,
        @ColorInt val titleTextColor: Int,
        val subTitleText: String?,
        val subTitleTextSize: Float,
        @ColorInt val subTitleTextColor: Int,
        val elementPadding: Int
    )
}