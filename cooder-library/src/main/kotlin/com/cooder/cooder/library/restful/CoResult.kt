package com.cooder.cooder.library.restful

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/10 21:29
 *
 * 介绍：LiveData的返回结果封装
 */
data class CoResult<T>(
	val data: T?,
	val msg: String? = null
) {
	
	fun isSuccessful(): Boolean {
		return data != null
	}
}