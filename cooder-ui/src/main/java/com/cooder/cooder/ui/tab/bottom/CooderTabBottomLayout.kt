package com.cooder.cooder.ui.tab.bottom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.*
import android.widget.AbsListView
import android.widget.FrameLayout
import android.widget.ScrollView
import androidx.annotation.FloatRange
import androidx.core.view.iterator
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.library.log.CooderLog
import com.cooder.cooder.library.util.CooderDisplayUtil
import com.cooder.cooder.library.util.CooderViewUtil
import com.cooder.cooder.library.util.dp
import com.cooder.cooder.ui.R
import com.cooder.cooder.ui.tab.bottom.CooderTabBottomLayout.DistanceType.*
import com.cooder.cooder.ui.tab.common.ICooderTabLayout
import kotlin.math.abs

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/28 18:09
 *
 * 介绍：CooderTabBottomLayout
 */
class CooderTabBottomLayout @JvmOverloads constructor(
	context: Context,
	attributeSet: AttributeSet? = null,
	defStyleAttr: Int = 0,
) : FrameLayout(context, attributeSet, defStyleAttr), ICooderTabLayout<CooderTabBottom, CooderTabBottomInfo<*>> {
	
	private val tabSelectedChangeListeners = mutableListOf<ICooderTabLayout.OnTabSelectedListener<CooderTabBottomInfo<*>>>()
	private var selectedInfo: CooderTabBottomInfo<*>? = null
	private val infoList = mutableListOf<CooderTabBottomInfo<*>>()
	
	private var bottomAlpha = DEFAULT_BOTTOM_ALPHA
	private var tabBottomHeight = DEFAULT_TAB_BOTTOM_HEIGHT
	private var bottomLineHeight = DEFAULT_BOTTOM_LINE_HEIGHT
	private var bottomLineColor = DEFAULT_BOTTOM_LINE_COLOR
	
	private var enableSliding = true
	private var params = SlidingPageParams(-1F, -1F, -1F, -1F, -1F, DISTANCE_MEDIUM)
	
	companion object {
		private const val TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM"
		
		const val DEFAULT_BOTTOM_ALPHA = 1F
		const val DEFAULT_TAB_BOTTOM_HEIGHT = 52F
		const val DEFAULT_BOTTOM_LINE_HEIGHT = 0.8F
		const val DEFAULT_BOTTOM_LINE_COLOR = "#DFE0E1"
		
		private val DISTANCE_SHORT = 30.dp
		private val DISTANCE_MEDIUM = 60.dp
		private val DISTANCE_LONG = 90.dp
	}
	
	enum class DistanceType {
		SHORT, MEDIUM, LONG
	}
	
	/**
	 * 设置滑动最小距离
	 */
	fun setSlidingMinDistance(type: DistanceType) {
		when (type) {
			SHORT -> params.minDistance = DISTANCE_SHORT
			MEDIUM -> params.minDistance = DISTANCE_MEDIUM
			LONG -> params.minDistance = DISTANCE_LONG
		}
	}
	
	
	fun setTabAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float) {
		this.bottomAlpha = alpha
	}
	
	/**
	 * 设置底部Tab的高度
	 */
	fun setTabHeight(tabHeight: Float) {
		this.tabBottomHeight = tabHeight
	}
	
	/**
	 * 设置底部Tab线的粗细
	 */
	fun setBottomLineHeight(lineHeight: Float) {
		this.bottomLineHeight = lineHeight
	}
	
	/**
	 * 设置底部Tab线的颜色
	 */
	fun setBottomLineColor(color: String) {
		this.bottomLineColor = color
	}
	
	/**
	 * 设置是否开启滑动切换页面
	 */
	fun setEnableSliding(enable: Boolean) {
		this.enableSliding = enable
	}
	
	/**
	 * 找到选中的Tab
	 */
	override fun findTab(info: CooderTabBottomInfo<*>): CooderTabBottom? {
		val tabBottomLayout: FrameLayout = findViewWithTag(TAG_TAB_BOTTOM)
		val iterator = tabBottomLayout.iterator()
		while (iterator.hasNext()) {
			val child = iterator.next()
			if (child is CooderTabBottom && child.getTabInfo() == info) {
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
		this.infoList.clear()
		this.infoList += infoList
		// 移除之前已经添加的View
		for (index in childCount - 1 downTo 1) {
			removeViewAt(index)
		}
		selectedInfo = null
		addBackground()
		
		// 清除之前添加的CooderTabBottom listener, Tip: Java foreach remove问题
		val iterator = tabSelectedChangeListeners.iterator()
		while (iterator.hasNext()) {
			if (iterator.next() is CooderTabBottom) iterator.remove()
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
			tabSelectedChangeListeners += tabBottom
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
	 * 选中导航项
	 */
	private fun onSelected(nextInfo: CooderTabBottomInfo<*>) {
		tabSelectedChangeListeners.forEach {
			it.onTabSelectedChange(infoList.indexOf(nextInfo), selectedInfo, nextInfo)
		}
		selectedInfo = nextInfo
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
		CooderLog.i("FIX1")
		if (getChildAt(0) !is ViewGroup) return
		CooderLog.i("FIX2")
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
	
	override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
		event?.also {
			if (enableSliding) slidingPageEvent(it)
		}
		return super.dispatchTouchEvent(event)
	}
	
	@SuppressLint("ClickableViewAccessibility")
	override fun onTouchEvent(event: MotionEvent?): Boolean {
		return true
	}
	
	/**
	 * 触碰水平滑动切换页面
	 */
	private fun slidingPageEvent(event: MotionEvent) {
		when (event.action) {
			MotionEvent.ACTION_DOWN -> {
				params.firstX = event.x
				params.firstY = event.y
			}
			MotionEvent.ACTION_MOVE -> {
				if (params.secondX == -1F && abs(params.firstX - event.x) > 5.dp) {
					params.secondX = event.x
					params.secondY = event.y
				}
				// 判断用户有没有在屏幕乱滑动防止勿触发切换页面
				if (params.secondX != -1F && params.secondY != -1F && params.prevX != -1F) {
					if ((params.firstX - params.secondX > 0 && params.prevX - event.x < 0) || (params.firstX - params.secondX < 0 && params.prevX - event.x > 0)) {
						params.onlyDirection = false
					}
				}
				// fix: 修复因上个点和下个点过近导致的勿触发乱点
				if (params.prevX == -1F || abs(params.prevX - event.x) > 5.dp) {
					params.prevX = event.x
				}
			}
			MotionEvent.ACTION_UP -> {
				if (params.onlyDirection && abs(params.firstX - event.x) >= params.minDistance) {
					if (abs(params.firstY - event.y) * 3 <= abs(params.firstX - event.x)) {
						if (params.firstX - event.x < 0) {          // prev
							onSelectedPrev()
						} else if (params.firstX - event.x > 0) {   // next
							onSelectedNext()
						}
					}
				}
				params = SlidingPageParams(-1F, -1F, -1F, -1F, -1F, params.minDistance)
			}
		}
	}
	
	/**
	 * 滑动页面切换所需要的参数
	 */
	private data class SlidingPageParams(
		var firstX: Float,
		var firstY: Float,
		var secondX: Float,
		var secondY: Float,
		var prevX: Float,
		var minDistance: Float,
		var onlyDirection: Boolean = true,
	)
}