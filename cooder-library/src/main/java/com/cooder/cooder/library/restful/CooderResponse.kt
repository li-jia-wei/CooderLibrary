package com.cooder.cooder.library.restful

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/11 14:58
 *
 * 介绍：相应报文
 */
open class CooderResponse<T> {
	
	companion object {
		const val SUCCESS = 0
		const val FAILURE = 1
	}
	
	/**
	 * 原始数据
	 */
	var rawData: String? = null
	
	/**
	 * 业务状态码
	 */
	var code: Int = 0
	
	/**
	 * 业务数据
	 */
	var data: T? = null
	
	/**
	 * 错误状态下的数据
	 */
	var errorData: Map<String, String>? = null
	
	/**
	 * 错误信息
	 */
	var msg: String? = null
}