package com.cooder.cooder.ui.banner

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager
import com.cooder.cooder.ui.R
import com.cooder.cooder.ui.banner.core.CoBannerDelegate
import com.cooder.cooder.ui.banner.core.CoBannerMo
import com.cooder.cooder.ui.banner.core.IBindAdapter
import com.cooder.cooder.ui.banner.core.ICoBanner
import com.cooder.cooder.ui.banner.indicator.CircleIndicator
import com.cooder.cooder.ui.banner.indicator.CoIndicator
import com.cooder.cooder.ui.banner.indicator.LineIndicator
import com.cooder.cooder.ui.banner.indicator.NumberIndicator

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/23 15:00
 *
 * 介绍：CoBanner
 */
class CoBanner @JvmOverloads constructor(
	context: Context,
	private val attrs: AttributeSet? = null,
	private val defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ICoBanner {
	
	private val delegate: CoBannerDelegate
	
	private var indicator: Int = -1
	
	private companion object {
		private const val CIRCLE_INDICATOR_SMALL = 0
		private const val CIRCLE_INDICATOR_MEDIUM = 1
		private const val CIRCLE_INDICATOR_LARGE = 2
		private const val LINE_INDICATOR = 10
		private const val NUMBER_INDICATOR = 20
	}
	
	init {
		delegate = CoBannerDelegate(context, this)
		initCustomAttrs()
	}
	
	private fun initCustomAttrs() {
		val typedArray: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.CoBanner)
		val autoPlay = typedArray.getBoolean(R.styleable.CoBanner_autoPlay, true)
		val loop = typedArray.getBoolean(R.styleable.CoBanner_loop, true)
		val intervalTime = typedArray.getInteger(R.styleable.CoBanner_intervalTime, -1)
		val scrollDuration = typedArray.getInteger(R.styleable.CoBanner_scrollDuration, -1)
		val closeIndicator = typedArray.getBoolean(R.styleable.CoBanner_closeIndicator, false)
		indicator = typedArray.getInteger(R.styleable.CoBanner_indicator, -1)
		setAutoPlay(autoPlay)
		setLoop(loop)
		setIntervalTime(intervalTime)
		setScrollDuration(scrollDuration)
		setBannerCloseIndicator(closeIndicator)
		typedArray.recycle()
	}
	
	override fun setBannerData(@LayoutRes layoutResId: Int, models: List<CoBannerMo>) {
		setBannerIndicatorAttrs()
		delegate.setBannerData(layoutResId, models)
	}
	
	override fun setBannerData(models: List<CoBannerMo>) {
		setBannerIndicatorAttrs()
		delegate.setBannerData(models)
	}
	
	override fun setBannerIndicator(indicator: CoIndicator<out View>) {
		delegate.setBannerIndicator(indicator)
	}
	
	override fun setBannerCloseIndicator(close: Boolean) {
		delegate.setBannerCloseIndicator(close)
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
	
	override fun setBindAdapter(bindAdapter: IBindAdapter) {
		delegate.setBindAdapter(bindAdapter)
	}
	
	override fun setScrollDuration(@IntRange(from = 100, to = 3000) duration: Int) {
		delegate.setScrollDuration(duration)
	}
	
	override fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener) {
		delegate.setOnPageChangeListener(onPageChangeListener)
	}
	
	override fun setOnBannerClickListener(onBannerClickListener: ICoBanner.OnBannerClickListener) {
		delegate.setOnBannerClickListener(onBannerClickListener)
	}
	
	override fun getBannerChildAt(position: Int): View? {
		return delegate.getBannerChildAt(position)
	}
	
	/**
	 * 设置指示器
	 */
	private fun setBannerIndicatorAttrs() {
		if (indicator != -1) {
			when (indicator) {
				CIRCLE_INDICATOR_SMALL -> delegate.setBannerIndicator(CircleIndicator(context, size = CircleIndicator.SMALL))
				CIRCLE_INDICATOR_MEDIUM -> delegate.setBannerIndicator(CircleIndicator(context, size = CircleIndicator.MEDIUM))
				CIRCLE_INDICATOR_LARGE -> delegate.setBannerIndicator(CircleIndicator(context, size = CircleIndicator.LARGE))
				LINE_INDICATOR -> delegate.setBannerIndicator(LineIndicator(context))
				NUMBER_INDICATOR -> delegate.setBannerIndicator(NumberIndicator(context))
			}
			indicator = -1
		}
	}
}