package com.cooder.library.ui.refresh

import com.cooder.library.ui.refresh.overview.CoOverView

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/11 00:05
 *
 * 介绍：下拉刷新接口
 */
interface CoRefresh {
	
	/**
	 * 刷新时是否禁止滚动
	 * @param disableRefreshScroll 禁止滚动刷新
	 */
	fun setDisableRefreshScroll(disableRefreshScroll: Boolean)
	
	/**
	 * 刷新完成
	 */
	fun refreshFinished()
	
	/**
	 * 设置下拉刷新的监听器
	 */
	fun setRefreshListener(refreshListener: CoRefreshListener)
	
	fun setRefreshOverView(overView: CoOverView)
	
	/**
	 * 下拉刷新的监听器
	 */
	interface CoRefreshListener {
		
		/**
		 * 刷新
		 */
		fun onRefresh()
		
		/**
		 * 是否开启刷新，默认true
		 */
		fun enableRefresh(): Boolean {
			return true
		}
	}
}