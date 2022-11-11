package com.cooder.cooder.library.restful

import androidx.annotation.IntDef
import java.lang.reflect.Type

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/11 15:07
 *
 * 介绍：CooderRequest
 */
open class CooderRequest {
	
	/**
	 * http方法
	 */
	@METHOD
	var httpMethod: Int = METHOD.NONE
	
	/**
	 * 请求头
	 */
	var headers: MutableMap<String, String>? = null
	
	/**
	 * 参数
	 */
	var parameters: MutableMap<String, Comparable<*>>? = null
	
	/**
	 * 域名
	 */
	var domainUrl: String? = null
	
	/**
	 * 相对路径
	 */
	var relativeUrl: String? = null
	
	/**
	 * 返回类型
	 */
	var returnType: Type? = null
	
	@IntDef(METHOD.NONE, METHOD.GET, METHOD.POST)
	internal annotation class METHOD {
		companion object {
			const val NONE = -1
			const val GET = 0
			const val POST = 1
		}
	}
}