package com.cooder.cooder.library.restful

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/10 21:29
 *
 * 介绍：数据类
 */
data class CoResult<T>(
	val data: T?,
	val success: Boolean = true,
	val msg: String? = null
) {
	
	fun isSuccessful(): Boolean {
		return success && data != null
	}
}