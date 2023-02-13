package com.cooder.cooder.library.restful

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/11 14:58
 *
 * 介绍：回调
 */
interface CoCallback<T> {
	
	fun onSuccess(response: CoResponse<T>)
	
	fun onFailed(throwable: Throwable)
}