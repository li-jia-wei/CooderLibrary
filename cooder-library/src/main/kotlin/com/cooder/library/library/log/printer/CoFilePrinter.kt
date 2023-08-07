package com.cooder.library.library.log.printer

import android.content.Context
import com.cooder.library.library.log.CoLogLevel
import com.cooder.library.library.log.config.CoLogConfig
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.Executors

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/26 19:01
 *
 * 介绍：日志文件打印
 */
class CoFilePrinter private constructor(
	private val context: Context
) : CoLogPrinter {
	
	companion object {
		
		private var instance: CoLogPrinter? = null
		
		fun getInstance(context: Context): CoLogPrinter {
			return instance ?: let {
				synchronized(CoConsolePrinter::class.java) {
					instance ?: let {
						instance = CoFilePrinter(context)
						instance!!
					}
				}
			}
		}
		
		internal val executor = Executors.newCachedThreadPool()
	}
	
	override fun print(config: CoLogConfig, level: CoLogLevel, tag: String, msg: String) {
		executor.execute {
			val logMo = CoLogMo(System.currentTimeMillis(), level, tag, msg)
			val logPath = "${context.filesDir.absolutePath}/log"
			createLogDir(logPath)
			val logFilename = "$logPath/${logMo.getFilename()}"
			writeLog(logFilename, "$logMo")
		}
	}
	
	/**
	 * 创建日志目录
	 */
	private fun createLogDir(dirname: String) {
		val file = File(dirname)
		if (file.exists() && file.isFile) {
			file.delete()
		}
		if (!file.exists()) {
			file.mkdir()
		}
	}
	
	/**
	 * 写入日志
	 */
	private fun writeLog(filename: String, content: String) {
		val file = File(filename)
		val isExists: Boolean
		try {
			isExists = file.exists()
			if (!isExists) {
				file.createNewFile()
			}
			val fos = FileOutputStream(file, true)
			fos.write("${if (isExists) "\n\n" else ""}$content".toByteArray())
			fos.flush()
			fos.close()
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}
}