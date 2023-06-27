package com.cooder.library.library.restful

import com.cooder.library.library.log.CoLog

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/11 14:58
 *
 * 介绍：Restful 回调
 */
interface CoCallback<T> {
	
	fun onSuccess(response: CoResponse<T>)
	
	fun onFailed(throwable: Throwable) {
		CoLog.e(throwable.message)
	}
}