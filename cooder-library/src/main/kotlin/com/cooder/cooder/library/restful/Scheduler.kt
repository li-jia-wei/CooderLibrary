package com.cooder.cooder.library.restful

import com.cooder.cooder.library.cache.CoStorage
import com.cooder.cooder.library.executor.CoExecutor
import com.cooder.cooder.library.log.CoLog
import com.cooder.cooder.library.restful.annotation.CacheStrategy
import com.cooder.cooder.library.util.CoMainHandler

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/11 20:40
 *
 * 介绍：代理CallFactory创建处理啊的call对象，实现拦截器的派发
 */
class Scheduler(
	private val callFactory: CoCall.Factory,
	private val interceptors: List<CoInterceptor>
) {
	
	/**
	 * 代理创建Call，实现拦截器的派发
	 */
	fun newCall(
		request: CoRequest,
		ignoreInterceptors: List<Class<out CoInterceptor>>?,
		extraInterceptors: List<Class<out CoInterceptor>>?
	): CoCall<*> {
		val delegate = callFactory.newCall(request)
		val finalInterceptors = if (extraInterceptors == null) interceptors else interceptors + generateExtraInterceptors(extraInterceptors)
		return ProxyCall(finalInterceptors, delegate, request, ignoreInterceptors)
	}
	
	/**
	 * 生成额外的拦截器
	 */
	private fun generateExtraInterceptors(extraInterceptorsClass: List<Class<out CoInterceptor>>): List<CoInterceptor> {
		val extraInterceptors = mutableListOf<CoInterceptor>()
		extraInterceptorsClass.forEach {
			extraInterceptors += it.newInstance()
		}
		return extraInterceptors
	}
	
	// Call代理
	private class ProxyCall<T>(
		private val interceptors: List<CoInterceptor>,
		private val delegate: CoCall<T>,
		private val request: CoRequest,
		private val ignoreInterceptors: List<Class<out CoInterceptor>>?
	) : CoCall<T> {
		
		override fun execute(): CoResponse<T> {
			dispatchRequestInterceptor(request)
			when (request.cacheStrategy) {
				CacheStrategy.CACHE_NET_CACHE -> {
					val cacheResponse = getCache(request)
					if (cacheResponse.data != null) {
						return cacheResponse
					}
				}
			}
			val response = delegate.execute()
			dispatchResponseInterceptor(response)
			saveCacheIfNeed(response)
			return response
		}
		
		override fun enqueue(callback: CoCallback<T>) {
			dispatchRequestInterceptor(request)
			
			if (request.cacheStrategy == CacheStrategy.CACHE_NET_CACHE) {
				CoExecutor.execute {
					val cacheResponse = getCache(request)
					if (cacheResponse.data != null) {
						CoMainHandler.sendMessageAtFrontOfQueue {
							callback.onSuccess(cacheResponse)
						}
						CoLog.i("enqueue, key=${request.getCacheKey()}")
					}
				}
			}
			
			delegate.enqueue(object : CoCallback<T> {
				override fun onSuccess(response: CoResponse<T>) {
					dispatchResponseInterceptor(response)
					
					saveCacheIfNeed(response)
					
					callback.onSuccess(response)
				}
				
				override fun onFailed(throwable: Throwable) {
					callback.onFailed(throwable)
				}
			})
		}
		
		private fun getCache(request: CoRequest): CoResponse<T> {
			val cacheKey = request.getCacheKey()
			val cache = CoStorage.getCache<T>(cacheKey)
			val cacheResponse = CoResponse<T>()
			cacheResponse.data = cache
			cacheResponse.code = CoResponse.CACHE_SUCCESS
			cacheResponse.message = "缓存获取成功"
			return cacheResponse
		}
		
		private fun saveCacheIfNeed(response: CoResponse<*>) {
			when (request.cacheStrategy) {
				CacheStrategy.NET_CACHE, CacheStrategy.CACHE_NET_CACHE -> {
					if (response.data != null) {
						CoExecutor.execute {
							CoStorage.saveCache(request.getCacheKey(), response.data)
						}
					}
				}
			}
		}
		
		/**
		 * request 调度拦截器
		 */
		private fun dispatchRequestInterceptor(request: CoRequest) {
			RequestInterceptor(request).dispatch()
		}
		
		/**
		 * response 调度拦截器
		 */
		private fun dispatchResponseInterceptor(response: CoResponse<*>) {
			ResponseInterceptor(response).dispatch()
		}
		
		/**
		 * Request 拦截器链
		 */
		private inner class RequestInterceptor(
			private val request: CoRequest,
		) : CoInterceptor.RequestChain {
			
			private var callIndex = 0
			
			override fun request(): CoRequest {
				return request
			}
			
			fun dispatch() {
				val interceptor = interceptors[callIndex]
				var intercept = false
				if (ignoreInterceptors == null || !ignoreInterceptors.contains(interceptor::class.java)) {
					intercept = interceptor.requestIntercept(this)
				}
				callIndex++
				if (!intercept && callIndex < interceptors.size) {
					dispatch()
				}
			}
		}
		
		/**
		 * Response 拦截器链
		 */
		private inner class ResponseInterceptor(
			private val response: CoResponse<*>
		) : CoInterceptor.ResponseChain {
			
			private var callIndex = 0
			
			override fun response(): CoResponse<*> {
				return response
			}
			
			fun dispatch() {
				val interceptor = interceptors[callIndex]
				var intercept = false
				if (ignoreInterceptors == null || !ignoreInterceptors.contains(interceptor::class.java)) {
					intercept = interceptor.responseIntercept(this)
				}
				callIndex++
				if (!intercept && callIndex < interceptors.size) {
					dispatch()
				}
			}
		}
	}
}