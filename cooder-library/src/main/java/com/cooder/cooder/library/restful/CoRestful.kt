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
 * 介绍：CoRestful
 */
open class CoRestful(
	private val baseUrl: String, callFactory: CoCall.Factory
) {
	
	private val interceptors = mutableListOf<CoInterceptor>()
	private val scheduler = Scheduler(callFactory, interceptors)
	private val methodService = mutableMapOf<Method, MethodParser>()
	
	fun addInterceptor(interceptor: CoInterceptor) {
		this.interceptors += interceptor
	}
	
	fun <T> create(service: Class<T>, cancelInterceptors: Array<out Class<out CoInterceptor>>): T {
		@Suppress("UNCHECKED_CAST")
		return Proxy.newProxyInstance(service.classLoader, arrayOf<Class<*>>(service)) { _: Any, method: Method, args: Array<Any>? ->
			var methodParser = methodService[method]
			if (methodParser == null) {
				methodParser = MethodParser.parse(baseUrl, method)
				methodService[method] = methodParser
			}
			val request = methodParser.newRequest(method, args)
			scheduler.newCall(request, cancelInterceptors)
		} as T
	}
}