package com.cooder.cooder.ui.banner

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager
import com.cooder.cooder.ui.R
import com.cooder.cooder.ui.banner.core.*
import com.cooder.cooder.ui.banner.indicator.CooderIndicator

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/23 15:00
 *
 * 介绍：CooderBanner
 */
class CooderBanner @JvmOverloads constructor(
	context: Context,
	attributeSet: AttributeSet,
	defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr), ICooderBanner {
	
	private val delegate: CooderBannerDelegate
	
	init {
		delegate = CooderBannerDelegate(context, this)
		initCustomAttrs(context, attributeSet)
	}
	
	private fun initCustomAttrs(context: Context, attrs: AttributeSet) {
		val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CooderBanner)
		val autoPlay = typedArray.getBoolean(R.styleable.CooderBanner_autoPlay, true)
		val loop = typedArray.getBoolean(R.styleable.CooderBanner_loop, true)
		val intervalTime = typedArray.getInteger(R.styleable.CooderBanner_intervalTime, -1)
		setAutoPlay(autoPlay)
		setLoop(loop)
		setIntervalTime(intervalTime)
		typedArray.recycle()
	}
	
	override fun setBannerData(@LayoutRes layoutResId: Int, models: List<CooderBannerMo>) {
		delegate.setBannerData(layoutResId, models)
	}
	
	override fun setBannerData(models: List<CooderBannerMo>) {
		delegate.setBannerData(models)
	}
	
	override fun setBannerIndicator(indicator: CooderIndicator<*>) {
		delegate.setBannerIndicator(indicator)
	}
	
	override fun setAutoPlay(autoPlay: Boolean) {
		delegate.setAutoPlay(autoPlay)
	}
	
	override fun setLoop(loop: Boolean) {
		delegate.setLoop(loop)
	}
	
	override fun setIntervalTime(@IntRange(from = 200, to = 10000) intervalTime: Int) {
		delegate.setIntervalTime(intervalTime)
	}
	
	override fun setBindAdapter(bindAdapter: (viewHolder: CooderBannerAdapter.CooderBannerViewHolder, bannerMo: CooderBannerMo, position: Int) -> Unit) {
		delegate.setBindAdapter(bindAdapter)
	}
	
	override fun setBindAdapter(bindAdapter: IBindAdapter) {
		delegate.setBindAdapter(bindAdapter)
	}
	
	override fun setScrollDuration(@IntRange(from = 100, to = 3000) duration: Int) {
		delegate.setScrollDuration(duration)
	}
	
	override fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener) {
		delegate.setOnPageChangeListener(onPageChangeListener)
	}
	
	override fun setOnBannerClickListener(onBannerClickListener: (viewHolder: CooderBannerAdapter.CooderBannerViewHolder, bannerMo: CooderBannerMo, position: Int) -> Unit) {
		delegate.setOnBannerClickListener(onBannerClickListener)
	}
	
	override fun setOnBannerClickListener(onBannerClickListener: ICooderBanner.OnBannerClickListener) {
		delegate.setOnBannerClickListener(onBannerClickListener)
	}
}