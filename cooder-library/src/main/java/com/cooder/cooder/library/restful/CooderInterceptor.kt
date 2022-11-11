package com.cooder.cooder.library.restful

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/11 15:17
 *
 * 介绍：拦截器
 */
interface CooderInterceptor {
	
	/**
	 * @return true:拦截, false:不拦截
	 */
	fun intercept(chain: Chain): Boolean
	
	/**
	 * Chain 会在派发拦截器的时候创建
	 */
	interface Chain {
		val isRequestPeriod: Boolean get() = false
		
		/**
		 * 请求
		 */
		fun request(): CooderRequest
		
		/**
		 * 这个response在网络发起前是为null的
		 */
		fun response(): CooderResponse<*>?
	}
}