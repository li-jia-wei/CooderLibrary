package com.cooder.library.ui.navigation

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.annotation.Px
import com.cooder.library.library.util.expends.dpInt
import com.cooder.library.library.util.expends.spInt
import com.cooder.library.ui.R

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/6/28 00:22
 *
 * 介绍：CoNavigationBar - AttrParse
 */
internal object AttrParse {
	
	private val NAV_ICON = R.string.ic_left
	private val NAV_COLOR = R.color.nav_color
	private const val BTN_TEXT_SIZE = 16
	private val BTN_TEXT_COLOR = R.color.nav_btn_text_color
	private const val TITLE_TEXT_SIZE = 18
	private const val TITLE_TEXT_SIZE_WITH_SUB = 16
	private val TITLE_TEXT_COLOR = R.color.nav_title_text_color
	private const val TITLE_STYLE = 1
	private const val SUB_TITLE_TEXT_SIZE = 11
	private val SUB_TITLE_TEXT_COLOR = R.color.nav_sub_title_text_color
	private const val ELEMENT_PADDING = 8
	private const val UNDER_LINE_ENABLED = true
	private const val UNDERLINE_HEIGHT = 3
	
	/**
	 * 解析参数
	 */
	fun parseAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int): Attr {
		val value = TypedValue()
		context.theme.resolveAttribute(R.attr.navigationStyle, value, true)
		val defStyleRes = if (value.resourceId != 0) value.resourceId else R.style.CoNavigationBarStyle
		val array = context.obtainStyledAttributes(attrs, R.styleable.CoNavigationBar, defStyleAttr, defStyleRes)
		
		val navIcon = array.getString(R.styleable.CoNavigationBar_navIcon) ?: context.getString(NAV_ICON)
		val navColor = array.getColor(R.styleable.CoNavigationBar_navColor, context.getColor(NAV_COLOR))
		val navAttr = NavAttr(navIcon, navColor)
		
		val btnTextSize = array.getDimensionPixelOffset(R.styleable.CoNavigationBar_btnTextSize, BTN_TEXT_SIZE.spInt).toFloat()
		val btnTextColor = array.getColor(R.styleable.CoNavigationBar_btnTextColor, context.getColor(BTN_TEXT_COLOR))
		val btnAttr = BtnAttr(btnTextSize, btnTextColor)
		
		val titleText = array.getString(R.styleable.CoNavigationBar_titleText)
		val titleTextSize = array.getDimensionPixelOffset(R.styleable.CoNavigationBar_titleTextSize, TITLE_TEXT_SIZE.spInt).toFloat()
		val titleTextSizeWithSub = array.getDimensionPixelOffset(R.styleable.CoNavigationBar_titleTextSizeWithSub, TITLE_TEXT_SIZE_WITH_SUB.spInt).toFloat()
		val titleTextColor = array.getColor(R.styleable.CoNavigationBar_titleTextColor, context.getColor(TITLE_TEXT_COLOR))
		val titleStyle = array.getInt(R.styleable.CoNavigationBar_titleStyle, TITLE_STYLE)
		val titleAttr = TitleAttr(titleText, titleTextSize, titleTextSizeWithSub, titleTextColor, titleStyle)
		
		val subTitleText = array.getString(R.styleable.CoNavigationBar_subTitleText)
		val subTitleTextSize = array.getDimensionPixelOffset(R.styleable.CoNavigationBar_subTitleTextSize, SUB_TITLE_TEXT_SIZE.spInt).toFloat()
		val subTitleTextColor = array.getColor(R.styleable.CoNavigationBar_subTitleTextColor, context.getColor(SUB_TITLE_TEXT_COLOR))
		val subTitleAttr = SubTitleAttr(subTitleText, subTitleTextSize, subTitleTextColor)
		
		val elementPadding = array.getDimensionPixelOffset(R.styleable.CoNavigationBar_elementPadding, ELEMENT_PADDING.dpInt)
		val elementAttr = ElementAttr(elementPadding)
		
		val underlineHeight = array.getDimensionPixelOffset(R.styleable.CoNavigationBar_underlineHeight, UNDERLINE_HEIGHT.dpInt).toFloat()
		val underlineEnabled = array.getBoolean(R.styleable.CoNavigationBar_underlineEnabled, UNDER_LINE_ENABLED)
		val underlineAttr = UnderlineAttr(underlineHeight, underlineEnabled)
		
		array.recycle()
		return Attr(navAttr, btnAttr, titleAttr, subTitleAttr, elementAttr, underlineAttr)
	}
	
	data class Attr(
		val navAttr: NavAttr,
		val btnAttr: BtnAttr,
		val titleAttr: TitleAttr,
		val subTitleAttr: SubTitleAttr,
		val elementAttr: ElementAttr,
		val underlineAttr: UnderlineAttr
	)
	
	data class NavAttr(
		val icon: String?,
		@ColorInt val color: Int
	)
	
	data class BtnAttr(
		@Px val textSize: Float,
		@ColorInt val textColor: Int
	)
	
	data class TitleAttr(
		val text: String?,
		@Px val textSize: Float,
		@Px val textSizeWithSub: Float,
		@ColorInt val textColor: Int,
		val style: Int
	)
	
	data class SubTitleAttr(
		val text: String?,
		@Px val textSize: Float,
		@ColorInt val textColor: Int
	)
	
	@JvmInline
	value class ElementAttr(
		@Px val padding: Int
	)
	
	data class UnderlineAttr(
		@Px val height: Float,
		val enabled: Boolean
	)
}