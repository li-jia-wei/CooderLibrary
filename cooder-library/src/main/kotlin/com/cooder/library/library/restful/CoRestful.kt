package com.cooder.library.library.restful

import com.cooder.library.library.restful.annotation.Api
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/11 15:22
 *
 * 介绍：Co网络请求框架
 */
open class CoRestful(
	private val baseUrl: String,
	callFactory: CoCall.Factory
) {
	
	private val interceptors = mutableListOf<CoInterceptor>()
	private val scheduler = Scheduler(callFactory, interceptors)
	private val methodService = mutableMapOf<Method, MethodParser>()
	
	fun addInterceptor(interceptor: CoInterceptor) {
		this.interceptors += interceptor
	}
	
	fun <T> create(
		service: Class<T>,
		ignoreInterceptors: List<Class<out CoInterceptor>>?,
		extraInterceptor: List<Class<out CoInterceptor>>?
	): T {
		if (!service.isInterface) throw IllegalStateException("${service.simpleName} 不是一个接口")
		if (!service.isAnnotationPresent(Api::class.java)) throw IllegalStateException("${service.simpleName} 不是一个Restful的Api，请标记 @Api")
		
		@Suppress("UNCHECKED_CAST")
		return Proxy.newProxyInstance(service.classLoader, arrayOf<Class<*>>(service)) { _: Any, method: Method, args: Array<Any>? ->
			var methodParser = methodService[method]
			if (methodParser == null) {
				methodParser = MethodParser.parse(baseUrl, method)
				methodService[method] = methodParser
			}
			val request = methodParser.newRequest(method, args)
			scheduler.newCall(request, ignoreInterceptors, extraInterceptor)
		} as T
	}
}