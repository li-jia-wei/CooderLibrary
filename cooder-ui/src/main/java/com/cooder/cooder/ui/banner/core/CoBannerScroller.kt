package com.cooder.cooder.ui.banner.core

import android.content.Context
import android.widget.Scroller

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/24 18:57
 *
 * 介绍：CooderBannercroller
 */
class CoBannerScroller(
	context: Context,
	private val mDuration: Int
) : Scroller(context) {
	
	override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
		super.startScroll(startX, startY, dx, dy, mDuration)
	}
	
	override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
		super.startScroll(startX, startY, dx, dy, mDuration)
	}
}