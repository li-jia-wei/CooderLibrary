package com.cooder.cooder.library.restful.annotation

import androidx.annotation.IntDef

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class CacheStrategy(
	@CacheStrategyDef val value: Int = NET_ONLY
) {
	
	companion object {
		
		/**
		 * 先缓存，后网络，再更新缓存
		 */
		const val CACHE_NET_CACHE = 0
		
		/**
		 * 只网络
		 */
		const val NET_ONLY = 1
		
		/**
		 * 先网络，后更新缓存
		 */
		const val NET_CACHE = 2
	}
	
	@IntDef(
		CACHE_NET_CACHE,
		NET_ONLY,
		NET_CACHE
	)
	annotation class CacheStrategyDef
}