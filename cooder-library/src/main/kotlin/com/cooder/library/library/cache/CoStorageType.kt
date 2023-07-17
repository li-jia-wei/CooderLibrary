package com.cooder.library.library.cache

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/13 23:55
 *
 * 介绍：CacheType
 */
enum class CoStorageType(val type: String) {
	/**
	 * 网络请求
	 */
	NET("net"),
	
	/**
	 * 数据量较小
	 */
	RECORD("record"),
	
	/**
	 * 配置
	 */
	CONFIG("config"),
	
	/**
	 * 数据量较大
	 */
	DATA("data")
}