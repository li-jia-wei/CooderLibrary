package com.cooder.cooder.library.log.printer

import com.cooder.cooder.library.log.CooderLogConfig
import com.cooder.cooder.library.log.CooderLogType

/**
 * 项目名称：CooderLibrary
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/9/24 13:49
 *
 * 文件介绍：CooderLogPrinter
 */
interface CooderLogPrinter {
	
	/**
	 * 打印
	 */
	fun print(config: CooderLogConfig, @CooderLogType.Type level: Int, tag: String, printString: String)
}