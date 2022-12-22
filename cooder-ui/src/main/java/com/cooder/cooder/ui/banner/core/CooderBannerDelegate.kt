package com.cooder.cooder.ui.banner.core

import android.content.Context
import android.widget.FrameLayout
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager
import com.cooder.cooder.ui.R
import com.cooder.cooder.ui.banner.CooderBanner
import com.cooder.cooder.ui.banner.indicator.CircleIndicator
import com.cooder.cooder.ui.banner.indicator.CooderIndicator

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/23 15:12
 *
 * 介绍：CooderBanner的控制器，辅助CooderBanner完成各种功能的控制
 *
 * 将CooderBanner的一些逻辑内聚在这，保证暴露给使用者的CooderBanner干净整洁
 */
class CooderBannerDelegate(
	private val context: Context,
	private val banner: CooderBanner
) : ViewPager.OnPageChangeListener, ICooderBanner {
	
	private var adapter: CooderBannerAdapter? = null
	private var indicator: CooderIndicator<*>? = null
	private var autoPlay = true
	private var loop = true
	private var bannerMos: List<CooderBannerMo>? = null
	private var onPageChangeListener: ViewPager.OnPageChangeListener? = null
	private var intervalTime = 5000
	private var viewPager: CooderViewPager? = null
	private var scrollDuration = -1
	
	private var isCallBannerData = false
	private var isCallBannerIndicator = false
	private var isCallBindAdapter = false
	
	override fun setBannerData(@LayoutRes layoutResId: Int, models: List<CooderBannerMo>) {
		if (!isCallBannerData) {
			isCallBannerData = true
			bannerMos = models
			init(layoutResId)
		}
	}
	
	override fun setBannerData(models: List<CooderBannerMo>) {
		if (!isCallBannerData) {
			isCallBannerData = true
			bannerMos = models
			init(R.layout.cooder_banner_item_image)
		}
	}
	
	/**
	 * 请在setBannerData方法之前执行，否则将只能使用默认指示器
	 */
	override fun setBannerIndicator(indicator: CooderIndicator<*>) {
		if (!isCallBannerData && !isCallBannerIndicator) {
			isCallBannerIndicator = true
			this.indicator = indicator
		} else {
			// 不能重复设置指示器
			throw IllegalStateException("You cannot set the indicator repeatedly.")
		}
	}
	
	override fun setAutoPlay(autoPlay: Boolean) {
		// 必须开启循环播放和自动播放才能开启自动播放，否则不能开启
		this.autoPlay = autoPlay && loop
		adapter?.setAutoPlay(this.autoPlay)
		viewPager?.setAutoPlay(this.autoPlay)
	}
	
	override fun setLoop(loop: Boolean) {
		// 只能初始化之前才能启动
		if (!isCallBannerData) {
			this.loop = loop
			adapter?.setLoop(loop)
		} else {
			throw IllegalStateException("Please call setLoop before setBannerData.")
		}
	}
	
	override fun setIntervalTime(@IntRange(from = 200, to = 10000) intervalTime: Int) {
		if (intervalTime in 200..10000) {
			this.intervalTime = intervalTime
			viewPager?.setIntervalTime(intervalTime)
		}
	}
	
	override fun setBindAdapter(bindAdapter: IBindAdapter) {
		if (isCallBannerData) {
			if (!isCallBindAdapter) {
				adapter?.setBindAdapter(bindAdapter)
			}
		} else {
			// 请先调用setBannerData方法
			throw IllegalStateException("Please call setBannerData first.")
		}
	}
	
	override fun setScrollDuration(@IntRange(from = 100, to = 3000) duration: Int) {
		if (!isCallBannerData) {
			if (duration in 100..3000) {
				this.scrollDuration = duration
				viewPager?.setScrollDuration(duration)
			}
		} else {
			throw IllegalStateException("Please call setScrollDuration before setBannerData.")
		}
	}
	
	override fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener) {
		this.onPageChangeListener = onPageChangeListener
	}
	
	override fun setOnBannerClickListener(onBannerClickListener: ICooderBanner.OnBannerClickListener) {
		this.adapter!!.setOnBannerClickListener(onBannerClickListener)
	}
	
	private fun init(layoutResId: Int) {
		if (adapter == null) adapter = CooderBannerAdapter(context)
		isCallBannerIndicator = true
		if (indicator == null) indicator = CircleIndicator(context, size = CircleIndicator.SMALL)
		bannerMos?.apply { indicator!!.onInflate(this.size) }
		adapter!!.apply {
			setLayoutResId(layoutResId)
			bannerMos?.let { setBannerData(it) }
			setAutoPlay(autoPlay)
			setLoop(loop)
		}
		
		if (viewPager == null) viewPager = CooderViewPager(context)
		viewPager!!.apply {
			setIntervalTime(intervalTime)
			setOnPageChangeListener(this@CooderBannerDelegate)
			setAutoPlay(autoPlay)
			adapter = this@CooderBannerDelegate.adapter
			if (scrollDuration in 100..3000) setScrollDuration(scrollDuration)
		}
		
		// 如果是循环滑动或者自动滚动
		if ((loop || autoPlay) && adapter!!.getRealCount() != 0) {
			val firstItem = adapter!!.getFirstItem()
			viewPager!!.setCurrentItem(firstItem, false)
		}
		banner.apply {
			// 清除所有View
			removeAllViews()
			val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
			addView(viewPager, params)
			indicator?.let {
				addView(it.get(), params)
			}
		}
	}
	
	override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
		if (adapter != null && adapter!!.getRealCount() != 0) {
			this.onPageChangeListener?.onPageScrolled(position % adapter!!.getRealCount(), positionOffset, positionOffsetPixels)
		}
	}
	
	override fun onPageSelected(position: Int) {
		if (adapter == null || adapter!!.getRealCount() == 0) return
		val realPosition = position % adapter!!.getRealCount()
		this.onPageChangeListener?.onPageSelected(realPosition)
		this.indicator?.onPointChange(realPosition, adapter!!.getRealCount())
	}
	
	override fun onPageScrollStateChanged(state: Int) {
		this.onPageChangeListener?.onPageScrollStateChanged(state)
	}
}