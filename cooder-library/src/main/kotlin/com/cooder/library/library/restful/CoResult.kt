package com.cooder.library.library.restful

import java.io.Serializable

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
	val msg: String?
) : Serializable {
	
	fun isSuccessful(): Boolean {
		return data != null
	}
	
	companion object {
		
		fun <T> success(data: T?, msg: String? = null): CoResult<T> {
			return CoResult(data, msg)
		}
		
		fun <T> failure(msg: String?): CoResult<T> {
			return CoResult(null, msg)
		}
	}
}