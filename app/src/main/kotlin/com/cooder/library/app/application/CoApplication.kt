package com.cooder.library.app.application

import android.app.Application
import com.cooder.cooder.library.log.CoLogConfig
import com.cooder.cooder.library.log.CoLogManager
import com.cooder.cooder.library.log.printer.CoConsolePrinter
import com.cooder.cooder.library.log.printer.CoFilePrinter

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/24 00:30
 *
 * 介绍：CooderApplication
 */
class CoApplication : Application() {
	
	override fun onCreate() {
		super.onCreate()
		initCooderLogManager()
	}
	
	/**
	 * 初始化日志系统
	 */
	private fun initCooderLogManager() {
		CoLogManager.init(this, object : CoLogConfig() {
			override fun enable(): Boolean {
				return true
			}
			
			override fun globalTag(): String {
				return "CooderLibraryTag"
			}
		}, CoConsolePrinter(), CoFilePrinter(this))
	}
}