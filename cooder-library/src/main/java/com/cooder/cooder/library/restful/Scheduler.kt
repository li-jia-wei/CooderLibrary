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
	private val callFactory: CoCall.Factory,
	val interceptors: MutableList<CoInterceptor>
) {
	
	/**
	 * 代理创建Call，实现拦截器的派发
	 */
	fun newCall(request: CoRequest, cancelInterceptors: Array<out Class<out CoInterceptor>>): CoCall<*> {
		val delegate = callFactory.newCall(request)
		return ProxyCall(delegate, request, cancelInterceptors)
	}
	
	// Call代理
	internal inner class ProxyCall<T>(
		private val delegate: CoCall<T>,
		private val request: CoRequest,
		private val cancelInterceptors: Array<out Class<out CoInterceptor>>
	) : CoCall<T> {
		
		override fun execute(): CoResponse<T> {
			dispatchRequestInterceptor(request)
			val response = delegate.execute()
			dispatchResponseInterceptor(response)
			return response
		}
		
		override fun enqueue(callback: CoCallback<T>) {
			dispatchRequestInterceptor(request)
			delegate.enqueue(object : CoCallback<T> {
				override fun onSuccess(response: CoResponse<T>) {
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
		internal inner class RequestInterceptor(
			private val request: CoRequest,
		) : CoInterceptor.RequestChain {
			
			private var callIndex = 0
			
			override fun request(): CoRequest {
				return request
			}
			
			fun dispatch() {
				val interceptor = interceptors[callIndex]
				var intercept = false
				if (!cancelInterceptors.contains(interceptor::class.java)) {
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
		internal inner class ResponseInterceptor(
			private val response: CoResponse<*>
		) : CoInterceptor.ResponseChain {
			
			private var callIndex = 0
			
			override fun response(): CoResponse<*> {
				return response
			}
			
			fun dispatch() {
				val interceptor = interceptors[callIndex]
				var intercept = false
				if (!cancelInterceptors.contains(interceptor::class.java)) {
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