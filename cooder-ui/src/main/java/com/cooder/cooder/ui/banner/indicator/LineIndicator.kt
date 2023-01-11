package com.cooder.cooder.ui.banner.indicator

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.forEachIndexed
import androidx.core.view.setMargins
import com.cooder.cooder.library.util.dpInt
import com.cooder.cooder.ui.R

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/24 20:27
 *
 * 介绍：横线指示器
 */
class LineIndicator @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), CooderIndicator<FrameLayout> {

	override fun get(): FrameLayout {
		return this
	}

	override fun onInflate(count: Int) {
		val groupView = LinearLayout(context)
		val groupViewParams = LayoutParams(LayoutParams.MATCH_PARENT, 5.dpInt)
		groupViewParams.gravity = Gravity.BOTTOM
		groupViewParams.setMargins(5.dpInt)
		var rect: View
		repeat(count) {
			rect = View(context)
			if (it == 0) {
				rect.setBackgroundResource(R.drawable.shape_banner_indicator_line)
			} else {
				rect.setBackgroundColor(Color.TRANSPARENT)
			}
			val rectParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT)
			rectParams.weight = 1F
			if (it == 0) {
				rectParams.setMargins(0, 0, 2.dpInt, 0)
			} else if (it == count - 1) {
				rectParams.setMargins(2.dpInt, 0, 0, 0)
			} else {
				rectParams.setMargins(2.dpInt, 0, 2.dpInt, 0)
			}
			groupView.addView(rect, rectParams)
		}
		addView(groupView, groupViewParams)
	}
	
	override fun onPointChange(current: Int, count: Int) {
		val groupView = getChildAt(0) as LinearLayout
		groupView.forEachIndexed { index, view ->
			if (index <= current) {
				view.setBackgroundResource(R.drawable.shape_banner_indicator_line)
			} else {
				view.setBackgroundColor(Color.TRANSPARENT)
			}
			view.requestLayout()
		}
	}
}