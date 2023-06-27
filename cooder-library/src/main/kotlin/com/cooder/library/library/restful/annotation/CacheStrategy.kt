package com.cooder.library.library.restful.annotation

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/6/27 19:15
 *
 * 介绍：缓存
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class CacheStrategy(
	val value: Type = Type.NET_ONLY
) {
	
	enum class Type {
		/**
		 * 先使用缓存并返回，再网络请求后返回，并存到缓存中
		 */
		CACHE_NET_CACHE,
		
		/**
		 * 只通过网络请求后返回
		 */
		NET_ONLY,
		
		/**
		 * 先网络请求后返回，并存到缓存中
		 */
		NET_CACHE,
		
		/**
		 * 只通过缓存返回
		 */
		CACHE_ONLY,
		
		/**
		 * 先通过缓存返回，再网络请求后存到缓存中，不返回
		 */
		CACHE_ONLY_NET_CACHE
	}
}