package com.cooder.cooder.library.log.format

/**
 * 项目名称：CooderLibrary
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/9/24 13:54
 *
 * 文件介绍：线程的格式化器
 */
class CooderThreadFormatter : CooderLogFormatter<Thread> {
	
	override fun format(data: Thread): String {
		return "Thread:${data.name} "
	}
}