package com.cooder.cooder.ui.banner.core

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.annotation.IntRange
import androidx.viewpager.widget.ViewPager
import java.lang.reflect.Field

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
	attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

	/**
	 * 自动播放时切换页面的间隔时间，默认：5000毫秒
	 */
	private var intervalTime: Int = 4000

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
			autoPlayHandler.postDelayed(this, intervalTime.toLong())
		}
	}
	
	/**
	 * 设置是否自动播放
	 */
	fun setAutoPlay(auto: Boolean) {
		this.autoPlay = auto
		if (!autoPlay) {
			autoPlayHandler.removeCallbacks(autoPlayRunnable)
		} else {
			start()
		}
	}
	
	/**
	 * 设置ViewPager的滚动速度
	 *
	 * @param duration page切换动画的时间长度
	 */
	fun setScrollDuration(@IntRange(from = 100, to = 3000) duration: Int) {
		setPrivateScrollerInViewPager(duration)
	}
	
	/**
	 * 设置切换间隔时间（单位：毫秒）
	 */
	fun setIntervalTime(intervalTime: Int) {
		this.intervalTime = intervalTime
	}
	
	/**
	 * 开始自动播放
	 */
	fun start() {
		autoPlayHandler.removeCallbacksAndMessages(null)
		if (autoPlay) {
			autoPlayHandler.postDelayed(autoPlayRunnable, intervalTime.toLong())
		}
	}
	
	/**
	 * 停止自动播放
	 */
	fun stop() {
		autoPlayHandler.removeCallbacksAndMessages(null)
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
			nextPosition = (adapter as CooderBannerAdapter).getFirstItem()
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
	 * 反射
	 *
	 * 将ViewPager中的私有字段mFirstLayout设置为false
	 */
	private fun setPrivateFirstLayoutInViewPagerFromTrueToFalse() {
		try {
			val firstLayoutField: Field = ViewPager::class.java.getDeclaredField("mFirstLayout")
			firstLayoutField.isAccessible = true
			firstLayoutField.set(this, false)
		} catch (e: NoSuchFieldException) {
			e.printStackTrace()
		}
	}
	
	/**
	 * 反射
	 *
	 * 将ViewPager中的私有字段mScroller设置为CooderBannerScroller，使能够自定义设置切换页面动画的长度
	 */
	private fun setPrivateScrollerInViewPager(duration: Int) {
		try {
			val scrollerField: Field = ViewPager::class.java.getDeclaredField("mScroller")
			scrollerField.isAccessible = true
			scrollerField.set(this, CooderBannerScroller(context, duration))
		} catch (e: NoSuchFieldException) {
			e.printStackTrace()
		}
	}
}