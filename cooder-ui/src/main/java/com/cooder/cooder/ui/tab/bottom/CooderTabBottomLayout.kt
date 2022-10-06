package com.cooder.cooder.ui.tab.bottom

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.FrameLayout
import android.widget.ScrollView
import androidx.annotation.FloatRange
import androidx.core.view.iterator
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.library.util.CooderDisplayUtil
import com.cooder.cooder.library.util.CooderViewUtil
import com.cooder.cooder.ui.R
import com.cooder.cooder.ui.tab.common.ICooderTabLayout

/**
 * 项目名称：CooderLibrary
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/9/28 18:09
 *
 * 文件介绍：CooderTabBottomLayout
 */
class CooderTabBottomLayout @JvmOverloads constructor(
	context: Context,
	attributeSet: AttributeSet? = null,
	defStyleAttr: Int = 0,
) : FrameLayout(context, attributeSet, defStyleAttr), ICooderTabLayout<CooderTabBottom, CooderTabBottomInfo<*>> {
	
	private val tabSelectedChangeListeners = mutableListOf<ICooderTabLayout.OnTabSelectedListener<CooderTabBottomInfo<*>>>()
	private var selectedInfo: CooderTabBottomInfo<*>? = null
	private lateinit var infoList: MutableList<CooderTabBottomInfo<*>>
	private var bottomAlpha = 1F
	private var tabBottomHeight = 50F
	private var bottomLineHeight = 0.5F
	private var bottomLineColor = "#DFE0E1"
	
	companion object {
		private const val TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM"
	}
	
	fun setTabAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float) {
		this.bottomAlpha = alpha
	}
	
	fun setTabHeight(tabHeight: Float) {
		this.tabBottomHeight = tabHeight
	}
	
	fun setBottomLineHeight(lineHeight: Float) {
		this.bottomLineHeight = lineHeight
	}
	
	fun setBottomLineColor(color: String) {
		this.bottomLineColor = color
	}
	
	/**
	 * 找到选中的Tab
	 */
	override fun findTab(data: CooderTabBottomInfo<*>): CooderTabBottom? {
		val tabBottomLayout: FrameLayout = findViewWithTag(TAG_TAB_BOTTOM)
		val iterator = tabBottomLayout.iterator()
		while (iterator.hasNext()) {
			val child = iterator.next()
			if (child is CooderTabBottom && child.getCooderTabInfo() == data) {
				return child
			}
		}
		return null
	}
	
	override fun addTabSelectedChangeListener(listener: ICooderTabLayout.OnTabSelectedListener<CooderTabBottomInfo<*>>) {
		tabSelectedChangeListeners += listener
	}
	
	override fun defaultSelected(defaultInfo: CooderTabBottomInfo<*>) {
		onSelected(defaultInfo)
	}
	
	override fun inflateInfo(infoList: List<CooderTabBottomInfo<*>>) {
		if (infoList.isEmpty()) return
		this.infoList = infoList.toMutableList()
		// 移除之前已经添加的View
		for (index in childCount - 1 downTo 1) {
			removeViewAt(index)
		}
		selectedInfo = null
		addBackground()
		
		// 清除之前添加的CooderTabBottom listener, Tip: Java foreach remove问题
		val iterator = tabSelectedChangeListeners.iterator()
		while (iterator.hasNext()) {
			if (iterator.next() is CooderTabBottom)
				iterator.remove()
		}
		
		val tabBottomLayout = FrameLayout(context)
		tabBottomLayout.tag = TAG_TAB_BOTTOM
		val width = CooderDisplayUtil.getDisplayWidth(context, CooderDisplayUtil.Unit.PX) / infoList.size
		val height = CooderDisplayUtil.dp2px(context, tabBottomHeight).toInt()
		for ((i, info) in infoList.withIndex()) {
			// tips: 为何不用LinearLayout，当动态改变child大小后Gravity.BOTTOM会失效
			val params = LayoutParams(width, height)
			params.gravity = Gravity.BOTTOM
			params.leftMargin = i * width
			val tabBottom = CooderTabBottom(context)
			tabSelectedChangeListeners.add(tabBottom)
			tabBottom.setTabInfo(info)
			tabBottomLayout.addView(tabBottom, params)
			tabBottom.setOnClickListener {
				onSelected(info)
			}
		}
		addBottomLine()
		val tabBottomParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
		tabBottomParams.gravity = Gravity.BOTTOM
		addView(tabBottomLayout, tabBottomParams)
		
		fixContentView()
	}
	
	/**
	 * 选中下一个导航项
	 */
	fun onSelectedNext() {
		val nextIndex = infoList.indexOf(selectedInfo) + 1
		if (nextIndex < infoList.size) {
			onSelected(infoList[nextIndex])
		}
	}
	
	/**
	 * 选中上一个导航项
	 */
	fun onSelectedPrev() {
		val prevIndex = infoList.indexOf(selectedInfo) - 1
		if (prevIndex >= 0) {
			onSelected(infoList[prevIndex])
		}
	}
	
	/**
	 * 选中导航项
	 */
	private fun onSelected(nextInfo: CooderTabBottomInfo<*>) {
		tabSelectedChangeListeners.forEach {
			it.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo)
		}
		selectedInfo = nextInfo
	}
	
	/**
	 * 设置背景
	 */
	private fun addBackground() {
		val view = LayoutInflater.from(context).inflate(R.layout.cooder_bottom_layout_bg, this, false)
		val params = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CooderDisplayUtil.dp2px(context, tabBottomHeight).toInt())
		params.gravity = Gravity.BOTTOM
		addView(view, params)
		view.alpha = bottomAlpha
	}
	
	/**
	 * 设置底部导航栏的横线
	 */
	private fun addBottomLine() {
		val bottomLine = View(context)
		bottomLine.setBackgroundColor(Color.parseColor(bottomLineColor))
		val bottomLineParams = LayoutParams(LayoutParams.MATCH_PARENT, CooderDisplayUtil.dp2px(context, bottomLineHeight).toInt())
		bottomLineParams.gravity = Gravity.BOTTOM
		bottomLineParams.bottomMargin = CooderDisplayUtil.dp2px(context, tabBottomHeight - bottomLineHeight).toInt()
		addView(bottomLine, bottomLineParams)
	}
	
	/**
	 * 修复内容区域的底部Padding
	 */
	private fun fixContentView() {
		if (getChildAt(0) !is ViewGroup) return
		val rootView = getChildAt(0) as ViewGroup
		var targetView: ViewGroup? = CooderViewUtil.findTypeView(rootView, RecyclerView::class.java)
		if (targetView == null) {
			targetView = CooderViewUtil.findTypeView(rootView, ScrollView::class.java)
		}
		if (targetView == null) {
			targetView = CooderViewUtil.findTypeView(rootView, AbsListView::class.java)
		}
		targetView?.apply {
			this.setPadding(0, 0, 0, CooderDisplayUtil.dp2px(context, tabBottomHeight).toInt())
			this.clipToPadding = false
		}
	}
}