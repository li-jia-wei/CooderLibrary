package com.cooder.cooder.library.log.printer

import com.cooder.cooder.library.log.CoLogConfig
import com.cooder.cooder.library.log.CoLogType

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/24 13:49
 *
 * 介绍：CoLogPrinter
 */
interface CoLogPrinter {
	
	/**
	 * 打印
	 */
	fun print(config: CoLogConfig, @CoLogType.Type level: Int, tag: String, printString: String)
}