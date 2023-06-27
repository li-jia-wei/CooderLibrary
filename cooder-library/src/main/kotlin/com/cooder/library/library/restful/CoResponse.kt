package com.cooder.library.library.restful

import java.io.Serializable

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/11 14:58
 *
 * 介绍：相应报文
 */
open class CoResponse<T> : Serializable {
	
	companion object {
		/**
		 * 成功
		 */
		const val SUCCESS = 0
		
		/**
		 * 缓存成功
		 */
		const val CACHE_SUCCESS = 304
		
		/**
		 * 有错误
		 */
		const val RC_HAS_ERROR = 5000
		
		/**
		 * 账号不存在
		 */
		const val RC_ACCOUNT_INVALID = 5001
		
		/**
		 * 密码错误
		 */
		const val RC_PWD_INVALID = 5002
		
		/**
		 * 请先登录
		 */
		const val RC_NEED_LOGIN = 5003
		
		/**
		 * 未购买本课程，或用户ID有误
		 */
		const val RC_NOT_PURCHASED = 5004
		
		/**
		 * 校验服务报错
		 */
		const val RC_CHECK_SERVER_ERROR = 5005
		
		/**
		 * 此用户名被占用
		 */
		const val RC_USERNAME_EXISTS = 5006
		
		/**
		 * 访问Token过期，请重新设置
		 */
		const val RC_AUTH_TOKEN_EXPIRED = 4030
		
		/**
		 * 访问Token不正确，请重新设置
		 */
		const val RC_AUTH_TOKEN_INVALID = 4031
		
		/**
		 * 用户身份非法
		 */
		const val RC_USER_FORBID = 6001
		
		/**
		 * 请输入HTML
		 */
		const val RC_HTML_INVALID = 8001
		
		/**
		 * 请输入配置
		 */
		const val RC_CONFIG_INVALID = 8002
		
		/**
		 * 出现异常
		 */
		const val EXCEPTION = -1
		
		const val serialVersionUID = 1L
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
	var message: String? = null
	
	/**
	 * 判断是否成功
	 */
	fun isSuccessful(): Boolean {
		return code == SUCCESS || code == CACHE_SUCCESS
	}
}