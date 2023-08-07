package com.cooder.library.library.log.config

import android.content.Context
import com.cooder.library.library.log.printer.CoFilePrinter
import com.cooder.library.library.log.printer.CoLogPrinter

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/8/6 22:01
 *
 * 介绍：正式版日志配置
 */
class CoReleaseLogConfig(
	context: Context,
	override val globalTag: String
) : CoLogConfig() {
	
	override val enable: Boolean = true
	
	override val printers: List<CoLogPrinter> = listOf(CoFilePrinter.getInstance(context))
}