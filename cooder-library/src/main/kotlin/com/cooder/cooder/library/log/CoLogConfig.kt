package com.cooder.cooder.library.log

import com.cooder.cooder.library.log.format.CoStackTraceFormatter
import com.cooder.cooder.library.log.format.CoThreadFormatter
import com.cooder.cooder.library.log.printer.CoLogPrinter

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
	
	/**
	 * 全局TAG
	 */
	open fun globalTag(): String {
		return "CooderTag"
	}
	
	/**
	 * 是否开启日志打印
	 */
	open fun enable(): Boolean {
		return false
	}
	
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
	open fun includeThread(): Boolean {
		return false
	}
	
	/**
	 * 是否包含堆栈信息
	 */
	open fun includeStackTrack(): Boolean {
		return false
	}
	
	/**
	 * 堆栈信息的深度，如设置-1则不限制长度
	 */
	open fun stackTrackDepth(): Int {
		return 5
	}
	
	/**
	 * 注册打印器
	 */
	open fun printers(): Array<CoLogPrinter>? {
		return null
	}
}