package com.cooder.cooder.library.log.printer

import android.content.Context
import com.cooder.cooder.library.log.CooderLogConfig
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.Executors

/**
 * 项目名称：CooderLibrary
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/9/26 19:01
 *
 * 文件介绍：CooderFilePrinter
 */
class CooderFilePrinter(private val context: Context) : CooderLogPrinter {
	
	companion object {
		internal val executor = Executors.newCachedThreadPool()
	}
	
	override fun print(config: CooderLogConfig, level: Int, tag: String, printString: String) {
		executor.execute {
			val logMo = CooderLogMo(System.currentTimeMillis(), level, tag, printString)
			val logPath = "${context.filesDir.absolutePath}/log"
			createLogDir(logPath)
			val logFilename = "$logPath/${logMo.getDate()}.log"
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