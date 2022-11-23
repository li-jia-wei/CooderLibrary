package com.cooder.cooder.library.restful

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
	private val callFactory: CooderCall.Factory,
	val interceptors: MutableList<CooderInterceptor>
) {
	
	/**
	 * 代理创建Call，实现拦截器的派发
	 */
	fun newCall(request: CooderRequest): CooderCall<*> {
		val delegate = callFactory.newCall(request)
		return ProxyCall(delegate, request)
	}
	
	// Call代理
	internal inner class ProxyCall<T>(
		private val delegate: CooderCall<T>,
		private val request: CooderRequest
	) : CooderCall<T> {
		
		override fun execute(): CooderResponse<T> {
			dispatchRequestInterceptor(request)
			val response = delegate.execute()
			dispatchResponseInterceptor(response)
			return response
		}
		
		override fun enqueue(callback: CooderCallback<T>) {
			dispatchRequestInterceptor(request)
			delegate.enqueue(object : CooderCallback<T> {
				override fun onSuccess(response: CooderResponse<T>) {
					dispatchResponseInterceptor(response)
					callback.onSuccess(response)
				}
				
				override fun onFailed(throwable: Throwable) {
					callback.onFailed(throwable)
				}
			})
		}
		
		/**
		 * request 调度拦截器
		 */
		private fun dispatchRequestInterceptor(request: CooderRequest) {
			RequestInterceptor(request).dispatch()
		}
		
		/**
		 * response 调度拦截器
		 */
		private fun dispatchResponseInterceptor(response: CooderResponse<*>) {
			ResponseInterceptor(response).dispatch()
		}
		
		/**
		 * Request 拦截器链
		 */
		internal inner class RequestInterceptor(
			private val request: CooderRequest
		) : CooderInterceptor.RequestChain {
			
			private var callIndex = 0
			
			override fun request(): CooderRequest {
				return request
			}
			
			fun dispatch() {
				val interceptor = interceptors[callIndex]
				val intercept = interceptor.requestIntercept(this)
				callIndex++
				if (!intercept && callIndex < interceptors.size) {
					dispatch()
				}
			}
		}
		
		/**
		 * Response 拦截器链
		 */
		internal inner class ResponseInterceptor(
			private val response: CooderResponse<*>
		) : CooderInterceptor.ResponseChain {
			
			private var callIndex = 0
			
			override fun response(): CooderResponse<*> {
				return response
			}
			
			fun dispatch() {
				val interceptor = interceptors[callIndex]
				val intercept = interceptor.responseIntercept(this)
				callIndex++
				if (!intercept && callIndex < interceptors.size) {
					dispatch()
				}
			}
		}
	}
}