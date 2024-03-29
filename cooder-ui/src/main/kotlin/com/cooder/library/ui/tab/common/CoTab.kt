package com.cooder.library.ui.tab.common

import androidx.annotation.Px

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/27 19:38
 *
 * 介绍：CoTab
 */
internal interface CoTab<Info : CoTabInfo<*>> : CoTabLayout.OnTabSelectedListener<Info> {
	
	fun setTabInfo(info: Info)
	
	/**
	 * 动态修改某个Item的大小
	 * @param height 高度
	 */
	fun resetHeight(@Px height: Int)
}