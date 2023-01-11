package com.cooder.cooder.ui.refresh.overview

import android.content.Context
import android.util.AttributeSet
import com.cooder.cooder.ui.refresh.overview.lottie.LottieAnim
import com.cooder.cooder.ui.refresh.overview.lottie.LottieType
import com.cooder.cooder.ui.refresh.overview.lottie.LottieType.WAVY
import com.cooder.cooder.ui.refresh.overview.lottie.LottieWavyAnim

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/14 17:35
 *
 * 介绍：CooderDotOverView
 */
class CooderLottieOverView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0,
	lottieType: LottieType = WAVY
) : CooderOverView(context, attrs, defStyleAttr) {

	private var lottieAnim: LottieAnim

	init {
		lottieAnim = when (lottieType) {
			WAVY -> LottieWavyAnim(context, this)
		}
	}

	override fun init() {

	}

	override fun onScroll(scrollY: Int, pullRefreshHeight: Int) {

	}

	override fun onVisible() {
		lottieAnim.visible()
	}
	
	override fun onOver() {
		lottieAnim.over()
	}
	
	override fun onRefresh() {
		lottieAnim.start()
	}
	
	override fun onFinish() {
		lottieAnim.stop()
	}
}