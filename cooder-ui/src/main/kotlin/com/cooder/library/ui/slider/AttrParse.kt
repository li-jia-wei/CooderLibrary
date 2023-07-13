package com.cooder.library.ui.slider

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import com.cooder.library.library.util.expends.dpInt
import com.cooder.library.library.util.expends.spInt
import com.cooder.library.ui.R

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/12 22:37
 *
 * 介绍：CoSliderView - AttrParse
 */
internal object AttrParse {
	
	private const val WIDTH = 100
	private const val HEIGHT = 45
	private const val TEXT_SIZE = 14
	private const val SELECT_TEXT_SIZE = 16
	private val TEXT_COLOR_NORMAL = R.color.slider_menu_text_normal
	private val TEXT_COLOR_SELECTED = R.color.slider_menu_text_selected
	private val BG_COLOR_NORMAL = R.color.slider_menu_bg_normal
	private val BG_COLOR_SELECTED = R.color.slider_menu_bg_selected
	private val INDICATOR = R.drawable.shape_slider_indicator
	
	fun parseAttr(context: Context, attrs: AttributeSet?): Attr {
		val array = context.obtainStyledAttributes(attrs, R.styleable.CoSliderView)
		val width: Int = array.getDimensionPixelOffset(R.styleable.CoSliderView_menuWidth, WIDTH.dpInt)
		val height: Int = array.getDimensionPixelOffset(R.styleable.CoSliderView_menuHeight, HEIGHT.dpInt)
		val textSize: Int = array.getDimensionPixelOffset(R.styleable.CoSliderView_menuTextSize, TEXT_SIZE.spInt)
		val selectTextSize: Int = array.getDimensionPixelOffset(R.styleable.CoSliderView_menuSelectTextSize, SELECT_TEXT_SIZE.spInt)
		val textColor: ColorStateList = array.getColorStateList(R.styleable.CoSliderView_menuTextColor) ?: generateTextColor(context)
		val indicator: Drawable? = array.getDrawable(R.styleable.CoSliderView_menuIndicator) ?: AppCompatResources.getDrawable(context, INDICATOR)
		val background: Drawable = array.getDrawable(R.styleable.CoSliderView_menuBackground) ?: ColorDrawable(context.getColor(BG_COLOR_NORMAL))
		val selectBackground: Drawable = array.getDrawable(R.styleable.CoSliderView_menuSelectBackground) ?: ColorDrawable(context.getColor(BG_COLOR_SELECTED))
		array.recycle()
		return Attr(width, height, textSize, selectTextSize, textColor, indicator, background, selectBackground)
	}
	
	data class Attr(
		var width: Int,
		var height: Int,
		var textSize: Int,
		var selectTextSize: Int,
		val textColor: ColorStateList,
		val indicator: Drawable?,
		val background: Drawable,
		val selectBackground: Drawable
	)
	
	private fun generateTextColor(context: Context): ColorStateList {
		val states = arrayOf(intArrayOf(android.R.attr.state_selected), intArrayOf())
		val colors = intArrayOf(context.getColor(TEXT_COLOR_SELECTED), context.getColor(TEXT_COLOR_NORMAL))
		return ColorStateList(states, colors)
	}
}