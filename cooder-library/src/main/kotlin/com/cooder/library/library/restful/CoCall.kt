package com.cooder.library.library.restful

import androidx.annotation.MainThread

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/11 15:03
 *
 * 介绍：调用
 */
interface CoCall<T> {
	
	/**
	 * 主线程上调用
	 *
	 * 支持所有注解类型
	 */
	@MainThread
	fun enqueue(callback: CoCallback<T>)
	
	/**
	 * 实现工厂
	 */
	interface Factory {
		
		fun newCall(request: CoRequest): CoCall<*>
	}
}