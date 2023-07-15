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
sealed class CoResult<T> {
	
	/**
	 * 成功数据模型
	 */
	data class Success<T>(val data: T) : CoResult<T>(), Serializable {
		
		companion object {
			const val serialVersionUID = 1L
		}
	}
	
	/**
	 * 失败数据模型
	 */
	data class Failure<T>(val msg: String, val code: Int) : CoResult<T>(), Serializable {
		
		companion object {
			const val serialVersionUID = 1L
		}
	}
}