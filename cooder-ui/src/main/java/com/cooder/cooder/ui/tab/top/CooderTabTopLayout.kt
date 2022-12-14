package com.cooder.cooder.ui.tab.top

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.annotation.IntRange
import androidx.core.view.iterator
import com.cooder.cooder.library.util.CooderDisplayUtil
import com.cooder.cooder.ui.tab.common.CooderTabLayout
import kotlin.math.abs

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/8 07:34
 *
 * 介绍：CooderTabTopLayout
 */
class CooderTabTopLayout @JvmOverloads constructor(
	context: Context,
	attributeSet: AttributeSet? = null,
	defStyleAttr: Int = 0,
) : HorizontalScrollView(context, attributeSet, defStyleAttr), CooderTabLayout<CooderTabTop, CooderTabTopInfo<*>> {
	
	private val tabSelectedChangeListeners = mutableListOf<CooderTabLayout.OnTabSelectedListener<CooderTabTopInfo<*>>>()
	private var selectedInfo: CooderTabTopInfo<*>? = null
	private val infoList = mutableListOf<CooderTabTopInfo<*>>()
	
	private var showInfoCount = 2
	
	private var tabWidth = 0
	
	init {
		isVerticalScrollBarEnabled = false
	}
	
	/**
	 * 设置左右显示Item的数量以展示更多的信息
	 */
	fun setShowInfoCount(@IntRange(from = 0, to = 3) count: Int) {
		this.showInfoCount = count
	}
	
	/**
	 * 找到选中的Tab
	 */
	override fun findTab(info: CooderTabTopInfo<*>): CooderTabTop? {
		val rootLayout = getRootLayout(clear = false)
		val iterator = rootLayout.iterator()
		while (iterator.hasNext()) {
			val child = iterator.next()
			if (child is CooderTabTop && child.getTabInfo() == info) {
				return child
			}
		}
		return null
	}
	
	override fun addTabSelectedChangeListener(listener: CooderTabLayout.OnTabSelectedListener<CooderTabTopInfo<*>>) {
		tabSelectedChangeListeners += listener
	}
	
	override fun selectTabInfo(tabInfo: CooderTabTopInfo<*>) {
		onSelected(tabInfo)
	}
	
	override fun inflateInfo(infoList: List<CooderTabTopInfo<*>>) {
		if (infoList.isEmpty()) return
		this.infoList.clear()
		this.infoList += infoList
		val rootLayout = getRootLayout(clear = true)
		
		selectedInfo = null
		val iterator = tabSelectedChangeListeners.iterator()
		while (iterator.hasNext()) {
			if (iterator.next() is CooderTabTop) iterator.remove()
		}
		infoList.forEach { info ->
			val tab = CooderTabTop(context)
			tabSelectedChangeListeners += tab
			tab.setTabInfo(info)
			rootLayout.addView(tab)
			tab.setOnClickListener {
				onSelected(info)
			}
		}
	}
	
	/**
	 * 获取外侧LinearLayout
	 * @param clear 是否清空子View
	 */
	private fun getRootLayout(clear: Boolean): LinearLayout {
		var rootView = getChildAt(0) as LinearLayout?
		if (rootView == null) {
			rootView = LinearLayout(context)
			rootView.orientation = LinearLayout.HORIZONTAL
			val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
			addView(rootView, params)
		} else if (clear) {
			rootView.removeAllViews()
		}
		return rootView
	}
	
	/**
	 * 选中导航项
	 */
	private fun onSelected(nextInfo: CooderTabTopInfo<*>) {
		tabSelectedChangeListeners.forEach {
			it.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo)
		}
		selectedInfo = nextInfo
		autoScroll(nextInfo)
	}
	
	/**
	 * 自动滚动，实现点击的位置能够自动滚动以展示前后Info
	 */
	private fun autoScroll(nextInfo: CooderTabTopInfo<*>) {
		val tabTop = findTab(nextInfo) ?: return
		val index = infoList.indexOf(nextInfo)
		val coords = IntArray(2)
		tabTop.getLocationInWindow(coords)
		if (tabWidth == 0) {
			tabWidth = tabTop.width
		}
		// 判断点击了屏幕左侧还是右侧
		val scrollWidth: Int = if (coords[0] + tabWidth / 2 > CooderDisplayUtil.getDisplayWidth(context, CooderDisplayUtil.Unit.PX) / 2) {
			rangeScrollWidth(index, showInfoCount)
		} else {
			rangeScrollWidth(index, -showInfoCount)
		}
		scrollTo(scrollX + scrollWidth, 0)
	}
	
	/**
	 * 获取可滚动的范围
	 * @param index 从第几个开始
	 * @param range 向前向后的范围
	 * @return 可滚动的范围
	 */
	private fun rangeScrollWidth(index: Int, range: Int): Int {
		var scrollWidth = 0
		for (i in 0..abs(range)) {
			val next = if (range < 0) range + i + index else range - i + index
			if (next >= 0 && next < infoList.size) {
				if (range < 0) {
					scrollWidth -= scrollWidth(next, false)
				} else {
					scrollWidth += scrollWidth(next, true)
				}
			}
		}
		return scrollWidth
	}
	
	/**
	 * 指定位置的控件可滚动的距离
	 * @param index 指定位置的控件
	 * @param toRight 是否是点击了屏幕右侧
	 * @return 可滚动的距离
	 */
	private fun scrollWidth(index: Int, toRight: Boolean): Int {
		val target = findTab(infoList[index]) ?: return 0
		val rect = Rect()
		target.getLocalVisibleRect(rect)
		return if (toRight) {
			// right坐标大于控件的宽度时，说明完全没有展示
			if (rect.right > tabWidth) tabWidth else tabWidth - rect.right
		} else {
			// left坐标小于等于控件的宽度时，说明完全没有显示
			if (rect.left <= -tabWidth) tabWidth else if (rect.left > 0) rect.left else 0
		}
	}
}