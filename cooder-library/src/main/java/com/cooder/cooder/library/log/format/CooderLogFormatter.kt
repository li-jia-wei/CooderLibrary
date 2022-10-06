package com.cooder.cooder.library.log.format

/**
 * 项目名称：CooderLibrary
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/9/24 13:52
 *
 * 文件介绍：CooderLogFormatter
 */
internal interface CooderLogFormatter<T> {
	
	/**
	 * 格式化
	 */
	fun format(data: T): String?
}