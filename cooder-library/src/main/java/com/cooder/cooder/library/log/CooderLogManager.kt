package com.cooder.cooder.library.log

import android.content.Context
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
	
	private val printers = mutableListOf<CooderLogPrinter>()
	
	init {
		this.printers += printers
	}
	
	companion object {
		private var instance: CooderLogManager? = null
		
		/**
		 * 初始化
		 */
		fun init(context: Context, config: CooderLogConfig, vararg printers: CooderLogPrinter) {
			// 此处无需加多线程，毕竟app启动就执行一次
			if (instance == null) {
				CooderLogFileUtil.clearTimeoutLogFile(context)
				instance = CooderLogManager(config, printers)
			}
		}
		
		/**
		 * 是否初始化
		 */
		fun isInit(): Boolean {
			return instance != null
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
		this.printers += printer
	}
	
	/**
	 * 移除一个打印器
	 */
	fun removePrinter(printer: CooderLogPrinter) {
		this.printers -= printer
	}
}