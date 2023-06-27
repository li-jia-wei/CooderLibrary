package com.cooder.library.ui.refresh.overview.lottie

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/14 18:19
 *
 * 介绍：LottieAnim
 */
internal interface LottieAnim {
	
	/**
	 * 初始化
	 */
	fun init()
	
	/**
	 * 可见
	 */
	fun visible()
	
	/**
	 * 超出
	 */
	fun over()
	
	/**
	 * 开始动画
	 */
	fun start()
	
	/**
	 * 停止动画
	 */
	fun stop()
}