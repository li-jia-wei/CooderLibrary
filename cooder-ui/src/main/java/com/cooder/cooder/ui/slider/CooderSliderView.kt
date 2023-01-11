package com.cooder.cooder.ui.slider

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.library.util.dpInt
import com.cooder.cooder.library.util.spInt
import com.cooder.cooder.ui.R

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/23 18:10
 *
 * 介绍：CooderSlider
 */
class CooderSliderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val menuItemAttr = parseMenuItemAttr(attrs)

    private val menuView = RecyclerView(context)
    private val contentView = RecyclerView(context)

    private companion object {
        private val MENU_ITEM_WIDTH = 100.dpInt
        private val MENU_ITEM_HEIGHT = 45.dpInt
        private val MENU_ITEM_TEXT_SIZE = 14.spInt

        private val TEXT_COLOR_NORMAL = R.color.cooder_slider_text_color_normal
        private val TEXT_COLOR_SELECT = R.color.cooder_slider_text_color_select

        private val BG_COLOR_NORMAL = R.color.cooder_slider_bg_color_normal
        private val BG_COLOR_SELECT = R.color.cooder_slider_bg_color_select
    }

    init {
        orientation = HORIZONTAL
        menuView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        menuView.overScrollMode = View.OVER_SCROLL_NEVER
        menuView.itemAnimator = null

        contentView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        contentView.overScrollMode = View.OVER_SCROLL_NEVER
        contentView.itemAnimator = null

        addView(menuView)
        addView(contentView)
    }

    fun bindMenuView(@LayoutRes layoutResId: Int) {

    }

    /**
     * 解析MenuItem属性
     */
    private fun parseMenuItemAttr(attrs: AttributeSet?): MenuItemAttr {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CooderSliderView)

        val width = typedArray.getDimensionPixelOffset(R.styleable.CooderSliderView_menuItemWidth, MENU_ITEM_WIDTH)
        val height = typedArray.getDimensionPixelOffset(R.styleable.CooderSliderView_menuItemHeight, MENU_ITEM_HEIGHT)
        val textSize = typedArray.getDimensionPixelSize(R.styleable.CooderSliderView_menuItemTextSize, MENU_ITEM_TEXT_SIZE)
        val selectTextSize = typedArray.getDimensionPixelSize(R.styleable.CooderSliderView_menuItemSelectTextSize, MENU_ITEM_TEXT_SIZE)
        val textColor = typedArray.getColorStateList(R.styleable.CooderSliderView_menuItemTextColor) ?: generateColorStateListForTextColor()
        val indicator = typedArray.getDrawable(R.styleable.CooderSliderView_menuItemIndicator) ?: ContextCompat.getDrawable(context, R.drawable.shape_slider_indicator)
        val background = typedArray.getDrawable(R.styleable.CooderSliderView_menuItemBackground)
        val selectBackground = typedArray.getDrawable(R.styleable.CooderSliderView_menuItemSelectBackground)
        val backgroundColor = typedArray.getColor(R.styleable.CooderSliderView_menuItemBackgroundColor, ContextCompat.getColor(context, BG_COLOR_NORMAL))
        val selectBackgroundColor = typedArray.getColor(R.styleable.CooderSliderView_menuItemSelectBackground, ContextCompat.getColor(context, BG_COLOR_SELECT))
        typedArray.recycle()

        return MenuItemAttr(
            width, height,
            textSize, selectTextSize,
            textColor,
            indicator,
            background, selectBackground,
            backgroundColor, selectBackgroundColor
        )
    }

    /**
     * MenuItem属性
     */
    private data class MenuItemAttr(
        val width: Int,
        val height: Int,
        val textSize: Int,
        val selectTextSize: Int,
        val textColor: ColorStateList,
        val indicator: Drawable?,
        val background: Drawable?,
        val selectBackground: Drawable?,
        val backgroundColor: Int,
        val selectBackgroundColor: Int
    )

    /**
     * 生成TextSize的ColorStateList
     */
    private fun generateColorStateListForTextColor(): ColorStateList {
        val states = arrayOf(intArrayOf(android.R.attr.state_selected), intArrayOf())
        val colors = intArrayOf(ContextCompat.getColor(context, TEXT_COLOR_SELECT), ContextCompat.getColor(context, TEXT_COLOR_NORMAL))
        return ColorStateList(states, colors)
    }
}