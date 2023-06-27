package com.cooder.library.ui.refresh.overview

import android.content.Context
import android.util.AttributeSet
import com.cooder.library.ui.refresh.overview.lottie.LottieAnim
import com.cooder.library.ui.refresh.overview.lottie.LottieType
import com.cooder.library.ui.refresh.overview.lottie.LottieWavyAnim

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/14 17:35
 *
 * 介绍：CoDotOverView
 */
class CoLottieOverView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0,
	lottieType: LottieType = LottieType.WAVY
) : CoOverView(context, attrs, defStyleAttr) {
	
	private var lottieAnim: LottieAnim
	
	init {
		lottieAnim = when (lottieType) {
			LottieType.WAVY -> LottieWavyAnim(context, this)
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