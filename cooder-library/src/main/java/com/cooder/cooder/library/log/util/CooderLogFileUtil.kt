package com.cooder.cooder.library.log.util

import android.content.Context
import com.cooder.cooder.library.log.printer.CooderFilePrinter
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/27 17:32
 *
 * 介绍：日志文件工具类
 */
object CooderLogFileUtil {
	
	private const val FILE_RETENTION_DAYS: Int = 30
	
	private const val MILLIS_IN_ONE_DAY: Long = 1000 * 60 * 60 * 24
	private const val FILE_RETENTION_MILLIS: Long = FILE_RETENTION_DAYS * MILLIS_IN_ONE_DAY
	
	private val SIMPLE_DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
	
	/**
	 * 检查超时的日志文件并删除
	 */
	fun checkLogFileForTimeoutAndClear(context: Context) {
		CooderFilePrinter.executor.execute {
			val logPath = context.filesDir.absolutePath + "/log"
			val dir = File(logPath)
			if (!dir.exists()) {
				dir.mkdir()
				return@execute
			}
			val now = Calendar.getInstance()
			now.get(Calendar.DAY_OF_YEAR)
			dir.listFiles()?.forEach {
				val date = it.name.substring(0, 10)
				if (isClear(date)) {
					it.delete()
				}
			}
		}
	}
	
	/**
	 * 判断是否需要清理
	 */
	private fun isClear(logDate: String): Boolean {
		val now = Calendar.getInstance(Locale.CHINA)
		now.set(now[Calendar.YEAR], now[Calendar.MONTH], now[Calendar.DAY_OF_MONTH], 0, 0, 0)
		val nowMillis = now.timeInMillis
		val log = Calendar.getInstance(Locale.CHINA)
		return try {
			log.time = SIMPLE_DATE_FORMAT.parse(logDate)!!
			log.set(log[Calendar.YEAR], log[Calendar.MONTH], log[Calendar.DAY_OF_MONTH], 0, 0, 0)
			val logMillis = log.timeInMillis
			nowMillis - logMillis > FILE_RETENTION_MILLIS
		} catch (e: Exception) {
			true
		}
	}
}