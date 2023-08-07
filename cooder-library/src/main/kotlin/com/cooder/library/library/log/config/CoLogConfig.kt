package com.cooder.library.library.log.config

import com.cooder.library.library.log.format.CoStackTraceFormatter
import com.cooder.library.library.log.format.CoThreadFormatter
import com.cooder.library.library.log.printer.CoLogPrinter

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/23 23:48
 *
 * 介绍：日志配置
 */
abstract class CoLogConfig {
	
	companion object {
		val CO_THREAD_FORMATTER = CoThreadFormatter()
		val CO_STACK_TRACE_FORMATTER = CoStackTraceFormatter()
		
		const val LOG_MAX_LENGTH = 3000
	}
	
	open val globalTag: String = "Cooder"
	
	/**
	 * 是否开启日志打印
	 */
	open val enable: Boolean = false
	
	/**
	 * 注入Json解析器
	 * @return JsonParser
	 */
	open fun injectJsonParser(): JsonParser? {
		return null
	}
	
	interface JsonParser {
		fun toJson(src: Any): String
	}
	
	/**
	 * 是否包含线程信息
	 */
	open val includeThread: Boolean = false
	
	/**
	 * 是否包含堆栈信息
	 */
	open val includeStackTrack: Boolean = false
	
	/**
	 * 堆栈信息的深度，如设置-1则不限制长度
	 */
	open val stackTrackDepth: Int = 5
	
	/**
	 * 注册打印器
	 */
	open val printers: List<CoLogPrinter>? = null
}