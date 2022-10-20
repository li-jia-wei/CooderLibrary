package com.cooder.cooder.ui.banner.core

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/20 15:48
 *
 * 介绍：CooderViewPager
 */
class CooderViewPager @JvmOverloads constructor(
	context: Context,
	attributeSet: AttributeSet? = null
) : ViewPager(context, attributeSet) {
	
	/**
	 * 自动播放时切换页面的间隔时间，默认：5000毫秒
	 */
	private var intervalTime: Long = 5000L
	
	/**
	 * 是否开启自动播放，默认：false
	 */
	private var autoPlay = true
	
	private var isLayout = false
	
	/**
	 * 自动播放处理
	 */
	private val autoPlayHandler = Handler(Looper.myLooper()!!)
	private val autoPlayRunnable = object : Runnable {
		override fun run() {
			// 切换到下一个
			next()
			autoPlayHandler.postDelayed(this, intervalTime)
		}
	}
	
	/**
	 * 设置是否自动播放
	 */
	fun setAutoPlay(auto: Boolean) {
		this.autoPlay = auto
		handler.removeCallbacks(autoPlayRunnable)
	}
	
	/**
	 * 设置切换间隔时间（单位：毫秒）
	 */
	fun setIntervalTime(intervalTime: Long) {
		this.intervalTime = intervalTime
	}
	
	/**
	 * 开始自动播放
	 */
	fun start() {
		handler.removeCallbacksAndMessages(null)
		if (autoPlay) {
			handler.postDelayed(autoPlayRunnable, intervalTime)
		}
	}
	
	/**
	 * 停止自动播放
	 */
	fun stop() {
		handler.removeCallbacksAndMessages(null)
	}
	
	/**
	 * 设置下一个要显示的Item，并返回Item的pos
	 *
	 * @return 下一个要显示的Item的pos
	 */
	private fun next(): Int {
		var nextPosition = -1
		if (adapter == null || adapter!!.count <= 1) {
			stop()
			return nextPosition
		}
		nextPosition = currentItem + 1
		if (nextPosition >= adapter!!.count) {
			// 获取第一个Item的索引
		}
		setCurrentItem(nextPosition, true)
		return nextPosition
	}
	
	override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
		super.onLayout(changed, l, t, r, b)
		isLayout = true
	}
	
	/**
	 * 手在视图上移动的时候不自动播放
	 */
	@SuppressLint("ClickableViewAccessibility")
	override fun onTouchEvent(ev: MotionEvent): Boolean {
		when (ev.action) {
			MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
				start()
			}
			else -> stop()
		}
		return super.onTouchEvent(ev)
	}
	
	/**
	 * fix: CooderViewPager在RecyclerView等控件中，上下滚动后卡在中间的问题
	 */
	override fun onAttachedToWindow() {
		super.onAttachedToWindow()
		if (isLayout && adapter != null && adapter!!.count > 0) {
			setPrivateFirstLayoutInViewPagerFromTrueToFalse()
		}
		start()
	}
	
	/**
	 * fix: CooderViewPager在RecyclerView等控件中，上下滚动后卡在中间的问题
	 */
	override fun onDetachedFromWindow() {
		if ((context as Activity).isFinishing) {
			super.onDetachedFromWindow()
		}
		stop()
	}
	
	/**
	 * 将ViewPager中的私有字段mFirstLayout设置为false
	 */
	private fun setPrivateFirstLayoutInViewPagerFromTrueToFalse() {
		try {
			val scroller = ViewPager::class.java.getDeclaredField("mFirstLayout")
			scroller.isAccessible = true
			scroller.set(this, false)
		} catch (e: NoSuchFieldException) {
			e.printStackTrace()
		}
	}
}