package com.cooder.cooder.library.restful

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
interface CooderCall<T> {
	
	@Throws(IOException::class)
	fun execute(): CooderResponse<T>
	
	fun enqueue(callback: CooderCallback<T>)
	
	/**
	 * 实现工厂
	 */
	interface Factory {
		
		fun newCall(request: CooderRequest): CooderCall<*>
	}
}