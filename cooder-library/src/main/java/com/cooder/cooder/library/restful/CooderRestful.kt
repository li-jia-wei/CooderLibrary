package com.cooder.cooder.library.restful

import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/11 15:22
 *
 * 介绍：CooderRestful
 */
open class CooderRestful(
	private val baseUrl: String,
	callFactory: CooderCall.Factory
) {
	
	private val interceptors = mutableListOf<CooderInterceptor>()
	private val scheduler = Scheduler(callFactory, interceptors)
	
	fun addInterceptor(interceptor: CooderInterceptor) {
		this.interceptors += interceptor
	}
	
	fun <T> create(service: Class<T>): T {
		@Suppress("UNCHECKED_CAST")
		return Proxy.newProxyInstance(service.classLoader, arrayOf<Class<*>>(service)) { _: Any, method: Method, args: Array<Any>? ->
			val methodParser = MethodParser.parse(baseUrl, method, args!!)
			val request = methodParser.newRequest()
			scheduler.newCall(request)
		} as T
	}
}