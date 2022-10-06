package com.cooder.cooder.library.log.printer

import android.util.Log
import com.cooder.cooder.library.log.CooderLogConfig
import com.cooder.cooder.library.log.CooderLogConfig.Companion.LOG_MAX_LENGTH

/**
 * 项目名称：CooderLibrary
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/9/24 16:43
 *
 * 文件介绍：CooderConsolePrinter
 */
class CooderConsolePrinter : CooderLogPrinter {
	
	override fun print(config: CooderLogConfig, level: Int, tag: String, printString: String) {
		val len = printString.length
		val countOfSub = len / LOG_MAX_LENGTH
		if (countOfSub > 0) {
			var index = 0
			repeat(countOfSub) {
				Log.println(level, tag, printString.substring(index, index + LOG_MAX_LENGTH))
				index += LOG_MAX_LENGTH
			}
			if (index != len) {
				Log.println(level, tag, printString.substring(index, len))
			}
		} else {
			Log.println(level, tag, printString)
		}
	}
}