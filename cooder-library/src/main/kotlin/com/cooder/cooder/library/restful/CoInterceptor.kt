package com.cooder.cooder.library.restful

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/11 15:17
 *
 * 介绍：Restful 拦截器
 */
interface CoInterceptor {
	
	/**
	 * request 拦截器
	 * @return true: 拦截
	 */
	fun requestIntercept(chain: RequestChain): Boolean
	
	/**
	 * response 拦截器
	 * @return true: 拦截
	 */
	fun responseIntercept(chain: ResponseChain): Boolean
	
	/**
	 * Request 派发拦截器的时候创建
	 */
	interface RequestChain {
		
		/**
		 * 获取request消息
		 */
		fun request(): CoRequest
	}
	
	/**
	 * Response 派发拦截器的时候创建
	 */
	interface ResponseChain {
		
		/**
		 * 获取response数据
		 */
		fun response(): CoResponse<*>
	}
}