package com.cooder.library.library.log.printer

import com.cooder.library.library.log.CoLogLevel
import com.cooder.library.library.log.config.CoLogConfig

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/24 13:49
 *
 * 介绍：日志打印
 */
interface CoLogPrinter {
	
	/**
	 * 打印
	 */
	fun print(config: CoLogConfig, level: CoLogLevel, tag: String, msg: String)
}