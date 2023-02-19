package com.cooder.cooder.ui.tab.bottom

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.*
import android.widget.AbsListView
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.ScrollView
import androidx.annotation.FloatRange
import androidx.annotation.Px
import androidx.core.view.iterator
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.library.util.CoDisplayUtil
import com.cooder.cooder.library.util.CoViewUtil
import com.cooder.cooder.library.util.expends.dp
import com.cooder.cooder.library.util.expends.dpInt
import com.cooder.cooder.ui.R
import com.cooder.cooder.ui.tab.bottom.CoTabBottomLayout.DistanceType.*
import com.cooder.cooder.ui.tab.common.CoTabLayout
import kotlin.math.abs

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/28 18:09
 *
 * 介绍：CoTabBottomLayout
 */
class CoTabBottomLayout @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr), CoTabLayout<CoTabBottom, CoTabBottomInfo<*>> {
	
	private val tabSelectedChangeListeners = mutableListOf<CoTabLayout.OnTabSelectedListener<CoTabBottomInfo<*>>>()
	private var selectedInfo: CoTabBottomInfo<*>? = null
	private val infoList = mutableListOf<CoTabBottomInfo<*>>()
	
	private var bottomAlpha = DEFAULT_BOTTOM_ALPHA
	private var tabBottomHeight = DEFAULT_TAB_BOTTOM_HEIGHT
	private var bottomLineHeight = DEFAULT_BOTTOM_LINE_HEIGHT
	private var bottomLineColor = DEFAULT_BOTTOM_LINE_COLOR
	
	private var enableSliding = false
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
		
		@JvmOverloads
		fun clipBottomPadding(targetView: ViewGroup, @Px height: Int = DEFAULT_TAB_BOTTOM_HEIGHT.dpInt) {
			targetView.setPadding(0, 0, 0, height)
			targetView.clipToPadding = false
		}
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
		// 如果没有可滚动的列表，那么可以切换
		this.enableSliding = enable
	}
	
	/**
	 * 获取底部高度
	 */
	fun getTabBottomLayoutHeight(): Float {
		return tabBottomHeight
	}
	
	/**
	 * 找到选中的Tab
	 */
	override fun findTab(info: CoTabBottomInfo<*>): CoTabBottom? {
		val tabBottomLayout: FrameLayout = findViewWithTag(TAG_TAB_BOTTOM)
		val iterator = tabBottomLayout.iterator()
		while (iterator.hasNext()) {
			val child = iterator.next()
			if (child is CoTabBottom && child.getTabInfo() == info) {
				return child
			}
		}
		return null
	}
	
	override fun addTabSelectedChangeListener(listener: CoTabLayout.OnTabSelectedListener<CoTabBottomInfo<*>>) {
		tabSelectedChangeListeners += listener
	}
	
	override fun selectTabInfo(tabInfo: CoTabBottomInfo<*>) {
		onSelected(tabInfo)
	}
	
	override fun inflateInfo(infoList: List<CoTabBottomInfo<*>>) {
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
			if (iterator.next() is CoTabBottom) iterator.remove()
		}
		
		val tabBottomLayout = FrameLayout(context)
		tabBottomLayout.tag = TAG_TAB_BOTTOM
		val width = CoDisplayUtil.getDisplayWidth(CoDisplayUtil.Unit.PX) / infoList.size
		val height = tabBottomHeight.dpInt
		for ((i, info) in infoList.withIndex()) {
			// tips: 为何不用LinearLayout，当动态改变child大小后Gravity.BOTTOM会失效
			val params = LayoutParams(width, height)
			params.gravity = Gravity.BOTTOM
			params.leftMargin = i * width
			val tabBottom = CoTabBottom(context)
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
	private fun onSelected(nextInfo: CoTabBottomInfo<*>) {
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
		val view = LayoutInflater.from(context).inflate(R.layout.co_bottom_layout_bg, this, false)
		val params = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, tabBottomHeight.dpInt)
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
		val bottomLineParams = LayoutParams(LayoutParams.MATCH_PARENT, bottomLineHeight.dpInt)
		bottomLineParams.gravity = Gravity.BOTTOM
		bottomLineParams.bottomMargin = (tabBottomHeight - bottomLineHeight).dpInt
		addView(bottomLine, bottomLineParams)
	}
	
	/**
	 * 修复内容区域的底部Padding
	 */
	fun fixContentView() {
		val rootView = getChildAt(0) as ViewGroup
		var targetView: ViewGroup? = CoViewUtil.findViewByType(rootView, RecyclerView::class.java)
		if (targetView == null) {
			targetView = CoViewUtil.findViewByType(rootView, ScrollView::class.java)
		}
		if (targetView == null) {
			targetView = CoViewUtil.findViewByType(rootView, AbsListView::class.java)
		}
		targetView?.apply {
			this.setPadding(0, 0, 0, tabBottomHeight.dpInt)
			this.clipToPadding = false
		}
	}
	
	override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
		event?.let {
			if (enableSliding && !hasScrollView()) slidingPageEvent(it)
		}
		return super.dispatchTouchEvent(event)
	}
	
	@SuppressLint("ClickableViewAccessibility")
	override fun onTouchEvent(event: MotionEvent?): Boolean {
		return if (enableSliding && !hasScrollView()) true else super.onTouchEvent(event)
	}
	
	/**
	 * 查看是否有可滚动的子View
	 */
	private fun hasScrollView(): Boolean {
		val viewGroup: ViewGroup? = CoViewUtil.findViewByType(this, HorizontalScrollView::class.java)
		return viewGroup != null
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