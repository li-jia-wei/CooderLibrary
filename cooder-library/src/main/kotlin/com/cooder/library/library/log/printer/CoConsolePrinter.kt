package com.cooder.library.library.log.printer

import android.util.Log
import com.cooder.library.library.log.CoLogConfig
import com.cooder.library.library.log.CoLogConfig.Companion.LOG_MAX_LENGTH

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/24 16:43
 *
 * 介绍：日志终端打印
 */
class CoConsolePrinter : CoLogPrinter {
	
	override fun print(config: CoLogConfig, level: Int, tag: String, printString: String) {
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