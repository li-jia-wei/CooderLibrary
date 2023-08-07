package com.cooder.library.library.log

import android.content.Context
import com.cooder.library.library.log.config.CoDisableLogConfig
import com.cooder.library.library.log.config.CoLogConfig
import com.cooder.library.library.log.printer.CoLogPrinter
import com.cooder.library.library.log.util.CoLogFileUtil

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/23 23:51
 *
 * 介绍：Log管理
 */
class CoLogManager private constructor(
	val config: CoLogConfig,
	printers: List<CoLogPrinter>? = null
) {
	
	private val printers = mutableListOf<CoLogPrinter>()
	
	init {
		printers?.let {
			this.printers += it
		}
	}
	
	companion object {
		private var instance: CoLogManager? = null
		
		/**
		 * 初始化
		 */
		fun init(context: Context, config: CoLogConfig, vararg printers: CoLogPrinter) {
			// 此处无需加多线程，毕竟app启动就执行一次
			if (instance == null) {
				CoLogFileUtil.clearTimeoutLogFile(context)
				instance = CoLogManager(config, if (printers.isNotEmpty()) printers.toList() else null)
			}
		}
		
		/**
		 * 获取实例
		 */
		fun getInstance(): CoLogManager {
			// 此处考虑可能会在多线程环境中执行，所以需要双重校验锁机制
			return instance ?: let {
				synchronized(this) {
					instance ?: let {
						instance = CoLogManager(CoDisableLogConfig())
						instance!!
					}
				}
			}
		}
	}
	
	/**
	 * 获取所有打印器
	 */
	fun getPrinters(): List<CoLogPrinter> {
		return printers
	}
	
	/**
	 * 增加一个打印器
	 */
	fun addPrinter(printer: CoLogPrinter) {
		this.printers += printer
	}
	
	/**
	 * 移除一个打印器
	 */
	fun removePrinter(printer: CoLogPrinter) {
		this.printers -= printer
	}
}