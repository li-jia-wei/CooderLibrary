package com.cooder.cooder.ui.banner.indicator

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.IntDef
import androidx.core.view.setMargins
import com.cooder.cooder.library.util.expends.dpInt
import com.cooder.cooder.ui.R

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/24 00:08
 *
 * 介绍：数字指示器
 */
class NumberIndicator @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), CoIndicator<FrameLayout> {
	
	@GravityDef
	private var gravity: Int = RIGHT_TOP
	
	private var isInflate = false
	
	companion object {
		private const val VMC = ViewGroup.LayoutParams.WRAP_CONTENT
		private val MARGIN = 6.dpInt

		const val LEFT_TOP = Gravity.START or Gravity.TOP
		const val LEFT_BOTTOM = Gravity.START or Gravity.BOTTOM
		const val RIGHT_TOP = Gravity.END or Gravity.TOP
		const val RIGHT_BOTTOM = Gravity.END or Gravity.BOTTOM
	}

	@IntDef(LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM)
	annotation class GravityDef
	
	override fun get(): FrameLayout {
		return this
	}
	
	override fun onInflate(count: Int) {
		isInflate = true
		val number = TextView(context)
		number.gravity = Gravity.CENTER
		number.setTextColor(Color.WHITE)
		number.textSize = 18F
		number.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
		val numberParams = LayoutParams(VMC, VMC)
		numberParams.gravity = gravity
		numberParams.setMargins(MARGIN)
		number.setBackgroundResource(R.drawable.shape_banner_indicator_number)
		addView(number, numberParams)
	}
	
	override fun onPointChange(current: Int, count: Int) {
		val number = getChildAt(0) as TextView
		number.text = String.format("%d", current + 1)
	}
	
	fun setGravity(@GravityDef gravity: Int) {
		if (isInflate) {
			val number = getChildAt(0) as TextView
			val params = number.layoutParams as LayoutParams
			params.gravity = gravity
			number.layoutParams = params
			number.requestLayout()
		}
		this.gravity = gravity
	}
}