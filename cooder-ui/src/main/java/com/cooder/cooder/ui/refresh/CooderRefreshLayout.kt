package com.cooder.cooder.ui.refresh

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import android.widget.Scroller
import com.cooder.cooder.ui.refresh.CooderOverView.CooderRefreshState.*
import com.cooder.cooder.ui.refresh.util.CooderScrollUtil
import kotlinx.coroutines.Runnable
import kotlin.math.abs

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/11 15:21
 *
 * 介绍：CooderRefreshLayout
 */
class CooderRefreshLayout @JvmOverloads constructor(
	context: Context,
	attributeSet: AttributeSet? = null,
	defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr), CooderRefresh {
	
	private var state: CooderOverView.CooderRefreshState = STATE_INIT
	private var gestureDetector: GestureDetector
	private var refreshListener: CooderRefresh.CooderRefreshListener? = null
	
	private var overView: CooderOverView? = null
	private var lastY: Int = 0
	
	private var disableRefreshScroll: Boolean = false
	
	private val autoScroller = AutoScroller()
	
	private val gestureDetectorListener = object : CooderGestureDetectorListener() {
		
		override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
			if (abs(distanceX) > abs(distanceY) || refreshListener != null && !refreshListener!!.enableRefresh()) {
				// 横向滑动或者刷新被禁止不处理滑动
				return false
			}
			if (disableRefreshScroll && state == STATE_REFRESH) {
				// 刷新时是否禁用滚动
				return true
			}
			val head = getChildAt(0)
			val child = CooderScrollUtil.findScrollableChild(this@CooderRefreshLayout)
			// 如果列表发送了滚动则不处理
			if (CooderScrollUtil.childScrolled(child)) {
				return false
			}
			// 没有刷新或没有达到可以刷新的距离，且头部已经划出或下拉
			if ((state != STATE_REFRESH || head.bottom <= CooderOverView.PULL_REFRESH_HEIGHT) && (head.bottom > 0 || distanceY <= 0F)) {
				// 如果还在滑动中
				if (state != STATE_OVER_RELEASE) {
					// 计算速度
					val speed = (if (child.top < CooderOverView.PULL_REFRESH_HEIGHT) {
						lastY / CooderOverView.MIN_DAMP
					} else {
						lastY / CooderOverView.MAX_DAMP
					}).toInt()
					// 如果是正在刷新状态，不允许在滑动的时候改变状态
					val bool = moveDown(speed, true)
				}
			}
			return super.onScroll(e1, e2, distanceX, distanceY)
		}
		
		/**
		 * 根据偏移量移动Header与Child
		 * @param offsetY 偏移量
		 * @param nonAuto 是否非自动滚动触发
		 */
		private fun moveDown(offsetY: Int, nonAuto: Boolean) {
			TODO("上次写到这里是: 2022-10-11 22:17:58")
		}
	}
	
	init {
		gestureDetector = GestureDetector(context, gestureDetectorListener)
	}
	
	override fun setDisableRefreshScroll(disableRefreshScroll: Boolean) {
		this.disableRefreshScroll = disableRefreshScroll
	}
	
	override fun refreshFinished() {
		
	}
	
	override fun setRefreshListener(refreshListener: CooderRefresh.CooderRefreshListener) {
		this.refreshListener = refreshListener
	}
	
	override fun setRefreshOverView(overView: CooderOverView) {
		this.overView = overView
	}
	
	override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
		event?.also { ev ->
			val head = getChildAt(0)
			when (ev.action) {
				ACTION_UP, ACTION_CANCEL, ACTION_POINTER_INDEX_MASK -> {
					if (head.bottom > 0) {
						if (state != STATE_REFRESH) {
							recover(head.bottom)
							return false
						}
					}
					lastY = 0
				}
			}
			val consumed = gestureDetector.onTouchEvent(ev)
			if ((consumed || (state != STATE_INIT && state != STATE_REFRESH)) && head.bottom != 0) {
				ev.action = ACTION_CANCEL   // 让父类接受不到真实事件
				super.dispatchTouchEvent(ev)
			}
			return if (consumed) true else super.dispatchTouchEvent(ev)
		}
		return super.dispatchTouchEvent(event)
	}
	
	/**
	 * 恢复视图
	 */
	private fun recover(dis: Int) {
		if (refreshListener != null && dis > CooderOverView.PULL_REFRESH_HEIGHT) {
			// 滚动到指定位置 dis - pullRefreshHeight
			autoScroller.recover(dis - CooderOverView.PULL_REFRESH_HEIGHT)
			state = STATE_OVER_RELEASE
		} else {
			autoScroller.recover(dis)
		}
	}
	
	/**
	 * 借助Scroller实现视图自动滚动
	 */
	private inner class AutoScroller : Runnable {
		
		private var scroller = Scroller(context, LinearInterpolator())
		private var lastY = 0
		
		var isFinished: Boolean = true
			private set
		
		override fun run() {
			if (scroller.computeScrollOffset()) {
				lastY = scroller.currY
				post(this)
			} else {
				removeCallbacks(this)
				isFinished = true
			}
		}
		
		fun recover(dis: Int) {
			if (dis < 0) return
			removeCallbacks(this)
			lastY = 0
			isFinished = false
			scroller.startScroll(0, 0, 0, dis, 300)
			post(this)
		}
	}
}