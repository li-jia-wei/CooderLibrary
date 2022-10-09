package com.cooder.cooder.ui.tab.common

import android.view.ViewGroup

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/27 19:32
 *
 * 介绍：ICooderTabLayout
 */
interface CooderTabLayout<Tab : ViewGroup, Info : CooderTabInfo<*>> {
	
	fun findTab(info: Info): Tab?
	
	fun addTabSelectedChangeListener(listener: OnTabSelectedListener<Info>)
	
	fun defaultSelected(defaultInfo: Info)
	
	fun inflateInfo(infoList: List<Info>)
	
	interface OnTabSelectedListener<Info : CooderTabInfo<*>> {
		fun onTabSelectedChange(index: Int, prevInfo: Info?, nextInfo: Info)
	}
}