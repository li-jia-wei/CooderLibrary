package com.cooder.cooder.ui.tab.common

import android.view.ViewGroup

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/27 19:32
 *
 * 介绍：CoTabLayout
 */
interface CoTabLayout<Tab : ViewGroup, Info : CoTabInfo<*>> {
	
	/**
	 * 查找Tab
	 */
	fun findTab(info: Info): Tab?
	
	/**
	 * 添加Tab切换事件
	 */
	fun addTabSelectedChangeListener(listener: OnTabSelectedListener<Info>)
	
	/**
	 * 添加Tab切换事件
	 */
	fun addTabSelectedChangeListener(listener: (index: Int, prevInfo: Info?, nextInfo: Info) -> Unit) {
		addTabSelectedChangeListener(object : OnTabSelectedListener<Info> {
			override fun onTabSelectedChange(index: Int, prevInfo: Info?, nextInfo: Info) {
				listener.invoke(index, prevInfo, nextInfo)
			}
		})
	}
	
	/**
	 * 选中TabInfo
	 */
	fun selectTabInfo(tabInfo: Info)
	
	/**
	 * 添加视图
	 */
	fun inflateInfo(infoList: List<Info>)
	
	/**
	 * Tab选中事件
	 */
	interface OnTabSelectedListener<Info : CoTabInfo<*>> {
		fun onTabSelectedChange(index: Int, prevInfo: Info?, nextInfo: Info)
	}
}