package com.cooder.cooder.ui.banner.core

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.ViewPager
import com.cooder.cooder.ui.R
import com.cooder.cooder.ui.banner.CoBanner
import com.cooder.cooder.ui.banner.indicator.CircleIndicator
import com.cooder.cooder.ui.banner.indicator.CoIndicator

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
class CoBannerDelegate(
	private val context: Context,
	private val banner: CoBanner
) : ViewPager.OnPageChangeListener, ICoBanner {
	
	private var adapter: CoBannerAdapter? = null
	private var indicator: CoIndicator<out View>? = null
	private var autoPlay = true
	private var loop = true
	private var bannerMos: List<CoBannerMo>? = null
	private var onPageChangeListener: ViewPager.OnPageChangeListener? = null
	private var intervalTime = 5000
	private var viewPager: CoViewPager? = null
	private var scrollDuration = -1
	
	private var isCallBannerData = false
	private var isCallBannerIndicator = false
    private var isCallBindAdapter = false
    private var closeIndicator = false
    private var isFirstSetCloseIndicator = true
	
	override fun setBannerData(@LayoutRes layoutResId: Int, models: List<CoBannerMo>) {
		if (!isCallBannerData) {
			isCallBannerData = true
			bannerMos = models
			init(layoutResId)
		}
	}
	
	override fun setBannerData(models: List<CoBannerMo>) {
		if (!isCallBannerData) {
			isCallBannerData = true
			bannerMos = models
			init(R.layout.co_banner_item_image)
		}
	}
	
	/**
	 * 请在setBannerData方法之前执行，否则将只能使用默认指示器
	 */
	override fun setBannerIndicator(indicator: CoIndicator<out View>) {
		if (!isCallBannerData && !isCallBannerIndicator) {
			isCallBannerIndicator = true
			this.indicator = indicator
		} else {
			// 不能重复设置指示器
			throw IllegalStateException("You cannot set the indicator repeatedly.")
		}
	}

    /**
     * 首次调用将不会判断指示器是否null，将指示器在init里完成，放置在设置不显示指示器的时候new出指示器，浪费内存
     */
    override fun setBannerCloseIndicator(close: Boolean) {
        this.closeIndicator = close
        // 当Indicator设置为开启的时候检查指示器是否为null，如果为null，初始化一个默认的
        if (!isFirstSetCloseIndicator && !close && indicator == null) {
            indicator = CircleIndicator(context, size = CircleIndicator.SMALL)
        }
        isFirstSetCloseIndicator = false
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
        if (intervalTime != -1) {
            this.intervalTime = if (intervalTime <= 200) 200 else if (intervalTime >= 10000) 10000 else intervalTime
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
            if (duration != -1) {
                this.scrollDuration = if (duration <= 100) 100 else if (duration >= 3000) 3000 else duration
                viewPager?.setScrollDuration(duration)
            }
        } else {
            throw IllegalStateException("Please call setScrollDuration before setBannerData.")
        }
    }

    override fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener) {
        this.onPageChangeListener = onPageChangeListener
    }
	
	override fun setOnBannerClickListener(onBannerClickListener: ICoBanner.OnBannerClickListener) {
		this.adapter!!.setOnBannerClickListener(onBannerClickListener)
	}
	
	fun <T : CoIndicator<out View>> getIndicator(): T {
		@Suppress("UNCHECKED_CAST")
		return indicator as T
	}

    private fun init(layoutResId: Int) {
	    if (adapter == null) adapter = CoBannerAdapter(context)
        isCallBannerIndicator = true
        if (!closeIndicator) {
            if (indicator == null) {
                indicator = CircleIndicator(context, size = CircleIndicator.SMALL)
            }
            if (bannerMos != null) {
                indicator!!.onInflate(bannerMos!!.size)
            }
        }
        adapter!!.apply {
            setLayoutResId(layoutResId)
            bannerMos?.let { setBannerData(it) }
            setAutoPlay(autoPlay)
            setLoop(loop)
        }
	
	    if (viewPager == null) viewPager = CoViewPager(context)
        viewPager!!.apply {
	        setIntervalTime(intervalTime)
	        setOnPageChangeListener(this@CoBannerDelegate)
	        setAutoPlay(autoPlay)
	        adapter = this@CoBannerDelegate.adapter
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
            if (!closeIndicator && indicator != null) {
                addView(indicator!!.get(), params)
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
        if (!closeIndicator) {
            this.indicator?.onPointChange(realPosition, adapter!!.getRealCount())
        }
    }
	
	override fun onPageScrollStateChanged(state: Int) {
		this.onPageChangeListener?.onPageScrollStateChanged(state)
	}
}