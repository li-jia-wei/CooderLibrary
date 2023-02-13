package com.cooder.cooder.library.restful

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import java.io.IOException

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
	 * 子线程调用
	 */
	@Throws(IOException::class)
	@WorkerThread
	fun execute(): CoResponse<T>
	
	/**
	 * 主线程上调用
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