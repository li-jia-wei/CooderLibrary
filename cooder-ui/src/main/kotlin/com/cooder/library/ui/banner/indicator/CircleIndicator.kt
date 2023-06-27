package com.cooder.library.ui.banner.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.annotation.IntDef
import androidx.core.view.forEachIndexed
import com.cooder.library.library.util.expends.dpInt
import com.cooder.library.ui.R

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
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0,
	@SizeDef private val size: Int = MEDIUM
) : FrameLayout(context, attrs, defStyleAttr), CoIndicator<FrameLayout> {
	
	/**
	 * 默认状态
	 */
	@DrawableRes
	private val pointNormal = arrayOf(
		R.drawable.shape_banner_indicator_point_small_normal,
		R.drawable.shape_banner_indicator_point_medium_normal,
		R.drawable.shape_banner_indicator_point_large_normal
	)
	
	/**
	 * 选中状态
	 */
	@DrawableRes
	private val pointSelected = arrayOf(
		R.drawable.shape_banner_indicator_point_small_selected,
		R.drawable.shape_banner_indicator_point_medium_selected,
		R.drawable.shape_banner_indicator_point_large_selected
	)
	
	/**
	 * 水平边距
	 */
	private val pointHorizontalMargin = when (size) {
		SMALL -> 2
		MEDIUM -> 3
		LARGE -> 4
		else -> 3
	}.dpInt
	
	/**
	 * 上下边距
	 */
	private val pointVerticalMargin = when (size) {
		SMALL -> 6
		MEDIUM -> 10
		LARGE -> 12
		else -> 10
	}.dpInt
	
	companion object {
		private const val VMC = ViewGroup.LayoutParams.WRAP_CONTENT
		
		const val SMALL = 0
		const val MEDIUM = 1
		const val LARGE = 2
	}
	
	@IntDef(SMALL, MEDIUM, LARGE)
	annotation class SizeDef
	
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
			point.setImageResource(if (it == 0) pointSelected[size] else pointNormal[size])
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
			point.setImageResource(if (index == current) pointSelected[size] else pointNormal[size])
			point.requestLayout()
		}
	}
}