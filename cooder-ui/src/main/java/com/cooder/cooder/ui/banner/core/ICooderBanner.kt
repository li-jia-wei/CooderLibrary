package com.cooder.cooder.ui.banner.core

import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager
import com.cooder.cooder.ui.banner.indicator.CooderIndicator
import com.cooder.cooder.ui.banner.indicator.IndicatorType

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/20 18:59
 *
 * 介绍：ICooderBanner
 */
interface ICooderBanner {
	
	/**
	 * 设置数据
	 */
	fun setBannerData(@LayoutRes layoutResId: Int, models: List<CooderBannerMo>)
	
	/**
	 * 设置数据
	 */
	fun setBannerData(models: List<CooderBannerMo>)
	
	/**
	 * 设置指示器
	 */
	fun setBannerIndicator(indicator: CooderIndicator<*>)
	
	/**
	 * 设置指示器类型
	 */
	fun setBannerIndicatorType(type: IndicatorType)
	
	/**
	 * 设置是否自动播放
	 */
	fun setAutoPlay(autoPlay: Boolean)
	
	/**
	 * 设置是否轮播
	 */
	fun setLoop(loop: Boolean)
	
	/**
	 * 设置在自动播放状态下切换延迟时长，单位: ms
	 */
	fun setIntervalTime(@IntRange(from = 200, to = 10000) intervalTime: Int)
	
	/**
	 * 设置绑定适配器
	 */
	fun setBindAdapter(bindAdapter: IBindAdapter)
	
	/**
	 * 设置滚动速度，单位: ms
	 */
	fun setScrollDuration(@IntRange(from = 100, to = 3000) duration: Int)
	
	/**
	 * 设置页面切换监听器
	 */
	fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener)
	
	/**
	 * 设置Banner点击事件
	 */
	fun setOnBannerClickListener(onBannerClickListener: OnBannerClickListener)
	
	interface OnBannerClickListener {
		fun onBannerClick(viewHolder: CooderBannerAdapter.CooderBannerViewHolder, bannerMo: CooderBannerMo, position: Int)
	}
}