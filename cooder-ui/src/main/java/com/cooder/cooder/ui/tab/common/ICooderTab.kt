package com.cooder.cooder.ui.tab.common

import androidx.annotation.Px

/**
 * 项目名称：CooderLibrary
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/9/27 19:38
 *
 * 文件介绍：ICooderTab
 */
interface ICooderTab<D> : ICooderTabLayout.OnTabSelectedListener<D> {
	
	fun setTabInfo(data: D)
	
	/**
	 * 动态修改某个Item的大小
	 * @param height 高度
	 */
	fun resetHeight(@Px height: Int)
}