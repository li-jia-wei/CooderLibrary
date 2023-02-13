package com.cooder.cooder.library.log.format

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/24 13:52
 *
 * 介绍：CoLogFormatter
 */
internal interface CoLogFormatter<T> {
	
	/**
	 * 格式化
	 */
	fun format(data: T): String?
}