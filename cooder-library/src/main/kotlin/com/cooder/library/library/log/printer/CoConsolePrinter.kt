package com.cooder.library.library.log.printer

import android.util.Log
import com.cooder.library.library.log.CoLogLevel
import com.cooder.library.library.log.config.CoLogConfig
import com.cooder.library.library.log.config.CoLogConfig.Companion.LOG_MAX_LENGTH

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/24 16:43
 *
 * 介绍：日志终端打印
 */
class CoConsolePrinter private constructor() : CoLogPrinter {
	
	companion object {
		
		private var instance: CoLogPrinter? = null
		
		fun getInstance(): CoLogPrinter {
			return instance ?: let {
				synchronized(CoConsolePrinter::class.java) {
					instance ?: let {
						instance = CoConsolePrinter()
						instance!!
					}
				}
			}
		}
		
		private val logs = mutableListOf<LogMo>()
		
		fun getLogs(): List<LogMo> {
			return logs
		}
	}
	
	data class LogMo(val time: Long, val level: CoLogLevel, val tag: String, val msg: String)
	
	override fun print(config: CoLogConfig, level: CoLogLevel, tag: String, msg: String) {
		val len = msg.length
		val countOfSub = len / LOG_MAX_LENGTH
		if (countOfSub > 0) {
			var index = 0
			repeat(countOfSub) {
				Log.println(level.level, tag, msg.substring(index, index + LOG_MAX_LENGTH))
				index += LOG_MAX_LENGTH
			}
			if (index != len) {
				Log.println(level.level, tag, msg.substring(index, len))
			}
		} else {
			Log.println(level.level, tag, msg)
		}
		logs += LogMo(System.currentTimeMillis(), level, tag, msg)
	}
}