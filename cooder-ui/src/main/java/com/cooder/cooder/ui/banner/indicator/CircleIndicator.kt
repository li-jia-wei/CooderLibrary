package com.cooder.cooder.ui.banner.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.view.forEachIndexed
import com.cooder.cooder.library.util.dp
import com.cooder.cooder.ui.R

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/24 00:08
 *
 * 介绍：圆形指示器
 */
class CircleIndicator @JvmOverloads constructor(
	context: Context,
	attributeSet: AttributeSet? = null,
	defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr), CooderIndicator<FrameLayout> {
	
	/**
	 * 默认状态
	 */
	@DrawableRes
	private val pointNormal = R.drawable.shape_indicator_point_normal
	
	/**
	 * 选中状态
	 */
	@DrawableRes
	private val pointSelected = R.drawable.shape_indicator_point_selected
	
	/**
	 * 水平边距
	 */
	private val pointHorizontalMargin = 5.dp.toInt()
	
	/**
	 * 上下边距
	 */
	private val pointVerticalMargin = 15.dp.toInt()
	
	companion object {
		private const val VMC = ViewGroup.LayoutParams.WRAP_CONTENT
	}
	
	override fun get(): FrameLayout {
		return this
	}
	
	override fun onInflate(count: Int) {
		removeAllViews()
		if (count <= 0) return
		val groupView = LinearLayout(context)
		groupView.orientation = LinearLayout.HORIZONTAL
		var point: ImageView
		val pointParams = LinearLayout.LayoutParams(VMC, VMC)
		pointParams.setMargins(pointHorizontalMargin, pointVerticalMargin, pointHorizontalMargin, pointVerticalMargin)
		repeat(count) {
			point = ImageView(context)
			point.setImageResource(if (it == 0) pointSelected else pointNormal)
			groupView.addView(point, pointParams)
		}
		val groupViewParams = LayoutParams(VMC, VMC)
		groupViewParams.gravity = Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
		addView(groupView, groupViewParams)
	}
	
	override fun onPointChange(current: Int, count: Int) {
		val viewGroup = getChildAt(0) as ViewGroup
		viewGroup.forEachIndexed { index: Int, view: View ->
			val point = view as ImageView
			point.setImageResource(if (index == current) pointSelected else pointNormal)
			point.requestLayout()
		}
	}
}