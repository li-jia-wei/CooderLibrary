package com.cooder.cooder.library.log

import android.content.Context
import com.cooder.cooder.library.log.printer.CooderFilePrinter
import com.cooder.cooder.library.log.printer.CooderLogPrinter
import com.cooder.cooder.library.log.util.CooderLogFileUtil

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/23 23:51
 *
 * 介绍：Log管理
 */
class CooderLogManager private constructor(
	val config: CooderLogConfig,
	printers: Array<out CooderLogPrinter>,
) {
	
	private val printers: MutableList<CooderLogPrinter> = ArrayList()
	
	init {
		this.printers += printers
	}
	
	companion object {
		@Volatile
		private var instance: CooderLogManager? = null
		
		/**
		 * 线程安全的初始化
		 */
		fun init(context: Context, config: CooderLogConfig, vararg printers: CooderLogPrinter) {
			if (instance == null) {
				synchronized(this) {
					if (instance == null) {
						printers.forEach {
							if (it is CooderFilePrinter) {
								CooderLogFileUtil.checkLogFileForTimeoutAndClear(context)
								return@forEach
							}
						}
						instance = CooderLogManager(config, printers)
					}
				}
			}
		}
		
		/**
		 * 获取实例
		 */
		fun getInstance(): CooderLogManager {
			return instance ?: throw IllegalStateException("暂未初始化CooderLogManager，请使用init方法初始化!")
		}
	}
	
	/**
	 * 获取所有打印器
	 */
	fun getPrinters(): List<CooderLogPrinter> {
		return printers
	}
	
	/**
	 * 增加一个打印器
	 */
	fun addPrinter(printer: CooderLogPrinter) {
		this.printers.add(printer)
	}
	
	/**
	 * 移除一个打印器
	 */
	fun removePrinter(printer: CooderLogPrinter) {
		this.printers.remove(printer)
	}
}