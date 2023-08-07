package com.cooder.library.library.log.config

import com.cooder.library.library.log.printer.CoConsolePrinter
import com.cooder.library.library.log.printer.CoLogPrinter

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/8/6 22:10
 *
 * 介绍：开启堆栈的日志配置
 */
class CoDebugStackTrackLogConfig(
	override val stackTrackDepth: Int,
	override val globalTag: String
) : CoLogConfig() {
	
	override val enable: Boolean = true
	
	override val includeStackTrack: Boolean = true
	
	override val printers: List<CoLogPrinter> = listOf(CoConsolePrinter.getInstance())
}