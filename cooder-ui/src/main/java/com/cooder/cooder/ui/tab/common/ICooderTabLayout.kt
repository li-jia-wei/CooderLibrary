package com.cooder.cooder.ui.tab.common

import android.view.ViewGroup

/**
 * 项目名称：CooderLibrary
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/9/27 19:32
 *
 * 文件介绍：ICooderTabLayout
 */
interface ICooderTabLayout<Tab : ViewGroup, D> {
	
	fun findTab(data: D): Tab?
	
	fun addTabSelectedChangeListener(listener: OnTabSelectedListener<D>)
	
	fun defaultSelected(defaultInfo: D)
	
	fun inflateInfo(infoList: List<D>)
	
	interface OnTabSelectedListener<D> {
		fun onTabSelectedChange(index: Int, prevInfo: D?, nextInfo: D)
	}
}