package com.cooder.library.ui.banner.indicator

import android.view.View

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/20 19:25
 *
 * 介绍：指示器统一接口，实现该接口来定义你需要样式的指示器
 */
interface CoIndicator<T : View> {
	
	fun get(): T
	
	/**
	 * 初始化Indicator
	 *
	 * @param count 幻灯片数量
	 */
	fun onInflate(count: Int)
	
	/**
	 * 幻灯片切换回调
	 *
	 * @param current 当前幻灯片的位置
	 * @param count 幻灯片数量
	 */
	fun onPointChange(current: Int, count: Int)
}