package com.cooder.library.library.log.format

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/24 13:54
 *
 * 介绍：线程的格式化器
 */
class CoThreadFormatter : CoLogFormatter<Thread> {
	
	override fun format(data: Thread): String {
		return "[${data.name}] "
	}
}