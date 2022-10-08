package com.cooder.cooder.library.log.printer

import com.cooder.cooder.library.log.CooderLogConfig
import com.cooder.cooder.library.log.CooderLogType

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/24 13:49
 *
 * 介绍：CooderLogPrinter
 */
interface CooderLogPrinter {
	
	/**
	 * 打印
	 */
	fun print(config: CooderLogConfig, @CooderLogType.Type level: Int, tag: String, printString: String)
}