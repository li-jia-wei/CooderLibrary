package com.cooder.library.ui.amount

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
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
 * 创建：2023/7/12 22:20
 *
 * 介绍：CoAmountView - AttrParse
 */
internal object AttrParse {
	
	private const val BTN_TEXT_SIZE = 16
	private val BTN_TEXT_COLOR = R.color.amount_btn_color
	private const val BTN_SIZE = 28
	private val BTN_BACKGROUND = R.color.amount_btn_bg
	private val BTN_BOUNDARY_BACKGROUND = R.color.amount_btn_boundary_bg
	private const val AMOUNT_TEXT_SIZE = 18
	private val AMOUNT_TEXT_COLOR = R.color.black
	private const val AMOUNT_SIZE = 28
	private val AMOUNT_BACKGROUND = R.color.white
	private const val AMOUNT_MARGIN = 4
	private const val VALUE = 1
	private const val MIN_VALUE = 0
	private const val MAX_VALUE = Int.MAX_VALUE
	
	fun parseAttr(context: Context, attrs: AttributeSet?, defStyleAttr: Int): Attr {
		val typedValue = TypedValue()
		context.theme.resolveAttribute(R.attr.amountStyle, typedValue, true)
		val defStyleRes = if (typedValue.resourceId != 0) typedValue.resourceId else R.style.CoAmountViewStyle
		val array = context.obtainStyledAttributes(attrs, R.styleable.CoAmountView, defStyleAttr, defStyleRes)
		val btnTextSize = array.getDimensionPixelOffset(R.styleable.CoAmountView_btnTextSize, BTN_TEXT_SIZE.spInt)
		val btnTextColor = array.getColor(R.styleable.CoAmountView_btnTextColor, context.getColor(BTN_TEXT_COLOR))
		val btnSize = array.getDimensionPixelOffset(R.styleable.CoAmountView_btnSize, BTN_SIZE.dpInt)
		val btnBackground = array.getDrawable(R.styleable.CoAmountView_btnBackground) ?: ColorDrawable(context.getColor(BTN_BACKGROUND))
		val btnBoundaryBackground = array.getDrawable(R.styleable.CoAmountView_btnBoundaryBackground) ?: ColorDrawable(context.getColor(BTN_BOUNDARY_BACKGROUND))
		val btnAttr = BtnAttr(btnTextSize, btnTextColor, btnSize, btnBackground, btnBoundaryBackground)
		
		val amountTextSize = array.getDimensionPixelOffset(R.styleable.CoAmountView_amountTextSize, AMOUNT_TEXT_SIZE.spInt)
		val amountTextColor = array.getColor(R.styleable.CoAmountView_amountTextColor, context.getColor(AMOUNT_TEXT_COLOR))
		val amountSize = array.getDimensionPixelOffset(R.styleable.CoAmountView_amountSize, AMOUNT_SIZE.dpInt)
		val amountBackground = array.getDrawable(R.styleable.CoAmountView_amountBackground) ?: ColorDrawable(context.getColor(AMOUNT_BACKGROUND))
		val amountMargin = array.getDimensionPixelOffset(R.styleable.CoAmountView_amountMargin, AMOUNT_MARGIN.dpInt)
		val amountAttr = AmountAttr(amountTextSize, amountTextColor, amountSize, amountBackground, amountMargin)
		
		val value = array.getInt(R.styleable.CoAmountView_value, VALUE)
		val minValue = array.getInt(R.styleable.CoAmountView_minValue, MIN_VALUE)
		val maxValue = array.getInt(R.styleable.CoAmountView_maxValue, MAX_VALUE)
		val valueAttr = ValueAttr(value, minValue, maxValue)
		array.recycle()
		
		return Attr(btnAttr, amountAttr, valueAttr)
	}
	
	data class Attr(
		val btnAttr: BtnAttr,
		val amountAttr: AmountAttr,
		val valueAttr: ValueAttr
	)
	
	data class BtnAttr(
		@Px val textSize: Int,
		@ColorInt val textColor: Int,
		@Px val size: Int,
		val background: Drawable,
		val boundaryBackground: Drawable
	)
	
	data class AmountAttr(
		@Px val textSize: Int,
		@ColorInt val textColor: Int,
		@Px val size: Int,
		val background: Drawable,
		@Px val margin: Int
	)
	
	data class ValueAttr(
		val value: Int,
		val minValue: Int,
		val maxValue: Int
	)
}