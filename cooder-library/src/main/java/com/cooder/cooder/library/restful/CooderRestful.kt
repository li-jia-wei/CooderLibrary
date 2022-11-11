package com.cooder.cooder.library.restful

import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap

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
	private val callFactory: CooderCall.Factory
) {
	
	private val interceptors = mutableListOf<CooderInterceptor>()
	private val methodParsers = ConcurrentHashMap<Method, MethodParser>()
	private val scheduler: Scheduler
	
	init {
		scheduler = Scheduler(callFactory, interceptors)
	}
	
	fun addInterceptor(interceptor: CooderInterceptor) {
		this.interceptors += interceptor
	}
	
	fun <T> create(service: Class<T>): T {
		@Suppress("UNCHECKED_CAST")
		return Proxy.newProxyInstance(service.classLoader, arrayOf<Class<*>>(service)) { proxy: Any, method: Method, args: Array<Any>? ->
			var methodParser = methodParsers[method]
			if (methodParser == null) {
				methodParser = MethodParser.parse(baseUrl, method, args as Array<Comparable<*>>)
				methodParsers[method] = methodParser
			}
			val request = methodParser.newRequest()
			scheduler.newCall(request)
		} as T
	}
}