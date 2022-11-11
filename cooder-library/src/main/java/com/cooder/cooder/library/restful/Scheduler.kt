package com.cooder.cooder.library.restful

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/11 20:40
 *
 * 介绍：代理CallFactory创建处理啊的call对象，实现拦截器的派发动作
 */
class Scheduler(
	private val callFactory: CooderCall.Factory,
	private val interceptors: MutableList<CooderInterceptor>
) {
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
			dispatchInterceptor(request, null)
			val response = delegate.execute()
			dispatchInterceptor(request, response)
			return response
		}
		
		override fun enqueue(callback: CooderCallback<T>) {
			dispatchInterceptor(request, null)
			delegate.enqueue(object : CooderCallback<T> {
				override fun onSuccess(response: CooderResponse<T>) {
					dispatchInterceptor(request, response)
					callback.onSuccess(response)
				}
				
				override fun onFailed(throwable: Throwable) {
					callback.onFailed(throwable)
				}
			})
		}
		
		private fun dispatchInterceptor(request: CooderRequest, response: CooderResponse<T>?) {
			InterceptorChain(request, response).dispatch()
		}
		
		/**
		 * 拦截器链
		 */
		internal inner class InterceptorChain(
			private val request: CooderRequest,
			private val response: CooderResponse<T>?
		) : CooderInterceptor.Chain {
			
			/**
			 * 分发的是第几个拦截器
			 */
			private var callIndex = 0
			
			override val isRequestPeriod: Boolean
				get() = response == null
			
			override fun request(): CooderRequest {
				return request
			}
			
			override fun response(): CooderResponse<*>? {
				return response
			}
			
			/**
			 * 派发拦截器
			 */
			fun dispatch() {
				val interceptor = interceptors[callIndex]
				val intercept = interceptor.intercept(this)
				callIndex++
				if (!intercept && callIndex < interceptors.size) {
					dispatch()
				}
			}
		}
	}
}