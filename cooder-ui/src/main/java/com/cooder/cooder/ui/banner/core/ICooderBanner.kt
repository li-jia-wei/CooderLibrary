package com.cooder.cooder.ui.banner.core

import android.view.View
import androidx.annotation.IntRange
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
	fun setBannerIndicator(indicator: CooderIndicator<out View>)

	/**
	 * 指示器是否关闭，默认：开启
	 */
	fun setBannerCloseIndicator(close: Boolean)

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
	fun setBindAdapter(bindAdapter: (viewHolder: CooderBannerAdapter.CooderBannerViewHolder, mo: CooderBannerMo, position: Int) -> Unit) {
		setBindAdapter(object : IBindAdapter {
			override fun onBind(viewHolder: CooderBannerAdapter.CooderBannerViewHolder, mo: CooderBannerMo, position: Int) {
				bindAdapter.invoke(viewHolder, mo, position)
			}
		})
	}
	
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
	fun setOnBannerClickListener(onBannerClickListener: (viewHolder: CooderBannerAdapter.CooderBannerViewHolder, mo: CooderBannerMo, position: Int) -> Unit) {
		setOnBannerClickListener(object : OnBannerClickListener {
			override fun onBannerClick(viewHolder: CooderBannerAdapter.CooderBannerViewHolder, mo: CooderBannerMo, position: Int) {
				onBannerClickListener.invoke(viewHolder, mo, position)
			}
		})
	}
	
	/**
	 * 设置Banner点击事件
	 */
	fun setOnBannerClickListener(onBannerClickListener: OnBannerClickListener)
	
	interface OnBannerClickListener {
		fun onBannerClick(viewHolder: CooderBannerAdapter.CooderBannerViewHolder, mo: CooderBannerMo, position: Int)
	}
}