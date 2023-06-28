package com.cooder.library.ui.navigation

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.ColorInt
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
 * 介绍：AttrsParse
 */
internal object AttrsParse {
	
	private const val BTN_TEXT_SIZE = 16
	private const val TITLE_TEXT_SIZE = 18
	private const val TITLE_TEXT_SIZE_WITH_SUB = 16
	private const val SUB_TITLE_TEXT_SIZE = 11
	private const val ELEMENT_PADDING = 8
	private const val UNDERLINE_HEIGHT = 3
	
	/**
	 * 解析参数
	 */
	fun parseNavAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int): NavAttrs {
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
		val underlineEnabled = array.getBoolean(R.styleable.CoNavigationBar_underlineEnabled, true)
		val underlineHeight = array.getDimensionPixelOffset(R.styleable.CoNavigationBar_underlineHeight, UNDERLINE_HEIGHT.dpInt).toFloat()
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
			elementPadding,
			underlineEnabled,
			underlineHeight
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
		val elementPadding: Int,
		val underlineEnabled: Boolean,
		val underlineHeight: Float
	)
}