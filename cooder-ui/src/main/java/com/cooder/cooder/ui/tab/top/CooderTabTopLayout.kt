package com.cooder.cooder.ui.tab.top

import android.content.Context
import android.util.AttributeSet
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.annotation.IntRange
import androidx.core.view.iterator
import com.cooder.cooder.library.util.CooderDisplayUtil
import com.cooder.cooder.ui.tab.common.ICooderTabLayout

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
) : HorizontalScrollView(context, attributeSet, defStyleAttr), ICooderTabLayout<CooderTabTop, CooderTabTopInfo<*>> {
	
	private val tabSelectedChangeListeners = mutableListOf<ICooderTabLayout.OnTabSelectedListener<CooderTabTopInfo<*>>>()
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
	
	override fun addTabSelectedChangeListener(listener: ICooderTabLayout.OnTabSelectedListener<CooderTabTopInfo<*>>) {
		tabSelectedChangeListeners += listener
	}
	
	override fun defaultSelected(defaultInfo: CooderTabTopInfo<*>) {
		onSelected(defaultInfo)
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
	 * 选中下一个导航项
	 */
	private fun onSelectedNext() {
		val nextIndex = infoList.indexOf(selectedInfo) + 1
		if (nextIndex < infoList.size) {
			onSelected(infoList[nextIndex])
		}
	}
	
	/**
	 * 选中上一个导航项
	 */
	private fun onSelectedPrev() {
		val prevIndex = infoList.indexOf(selectedInfo) - 1
		if (prevIndex >= 0) {
			onSelected(infoList[prevIndex])
		}
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
		if (coords[0] + tabWidth / 2 > CooderDisplayUtil.getDisplayWidth(context) / 2) {
		
		}
	}
}