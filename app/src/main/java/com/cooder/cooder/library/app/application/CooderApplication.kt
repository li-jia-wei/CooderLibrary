package com.cooder.cooder.library.app.application

import android.app.Application
import com.cooder.cooder.library.log.CooderLogConfig
import com.cooder.cooder.library.log.CooderLogManager
import com.cooder.cooder.library.log.printer.CooderConsolePrinter
import com.cooder.cooder.library.log.printer.CooderFilePrinter

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/24 00:30
 *
 * 介绍：CooderApplication
 */
class CooderApplication : Application() {
	
	override fun onCreate() {
		super.onCreate()
		initCooderLogManager()
	}
	
	/**
	 * 初始化日志系统
	 */
	private fun initCooderLogManager() {
		CooderLogManager.init(this, object : CooderLogConfig() {
			override fun globalTag(): String {
				return "CooderLibraryTAG"
			}
		}, CooderConsolePrinter(), CooderFilePrinter(this))
	}
}