package com.cooder.library.library.log.printer

import com.cooder.library.library.log.CoLogLevel
import com.cooder.library.library.log.CoLogLevel.ASSERT
import com.cooder.library.library.log.CoLogLevel.DEBUG
import com.cooder.library.library.log.CoLogLevel.ERROR
import com.cooder.library.library.log.CoLogLevel.INFO
import com.cooder.library.library.log.CoLogLevel.VERBOSE
import com.cooder.library.library.log.CoLogLevel.WARN
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/26 10:40
 *
 * 介绍：日志模型
 */
internal data class CoLogMo(
	private val timeMillis: Long,
	val level: CoLogLevel,
	private val tag: String,
	val log: String,
) {
	
	fun getFilename(): String {
		val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
		return "${sdf.format(timeMillis)}.log"
	}
	
	private fun getFlattened(): String {
		return "${format(timeMillis)} ${getLevel(level)}/$tag:"
	}
	
	private fun getLevel(level: CoLogLevel): String {
		return when (level) {
			VERBOSE -> "V"
			DEBUG -> "D"
			INFO -> "I"
			WARN -> "W"
			ERROR -> "E"
			ASSERT -> "A"
		}
	}
	
	private fun format(timeMillis: Long): String {
		val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
		return sdf.format(timeMillis)
	}
	
	override fun toString(): String {
		return "${getFlattened()} $log"
	}
}