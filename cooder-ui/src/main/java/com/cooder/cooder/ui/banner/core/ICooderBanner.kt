package com.cooder.cooder.ui.banner.core

import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager
import com.cooder.cooder.ui.banner.indicator.CooderIndicator

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
	
	fun setBannerData(@LayoutRes layoutResId: Int, models: List<CooderBannerMo>)
	
	fun setBannerData(models: List<CooderBannerMo>)
	
	fun setBannerIndicator(cooderIndicator: CooderIndicator<*>)
	
	fun setAutoPlay(autoPlay: Boolean)
	
	fun setLoop(loop: Boolean)
	
	fun setIntervalTime(intervalTime: Long)
	
	fun setBindAdapter(bindAdapter: IBindAdapter)
	
	fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener)
	
	fun setOnBannerClickListener(onBannerClickListener: OnBannerClickListener)
	
	interface OnBannerClickListener {
		fun onBannerClick(viewHolder: CooderBannerAdapter.CooderBannerViewHolder, bannerMo: CooderBannerMo, position: Int)
	}
}