package com.cooder.cooder.app.ui.log

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.cooder.cooder.app.R
import com.cooder.cooder.library.log.CooderLog
import com.cooder.cooder.library.log.CooderLogConfig
import com.cooder.cooder.library.log.CooderLogManager
import com.cooder.cooder.library.log.CooderLogType
import com.cooder.cooder.library.log.printer.CooderViewPrinter

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/24 00:34
 *
 * 介绍：日志Demo
 */
class CooderLogActivity : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_cooder_log)
		
		val viewPrinter = CooderViewPrinter(this)
		viewPrinter.getViewProvider().showFloatingView()
		CooderLogManager.getInstance().addPrinter(viewPrinter)
		
		val printLog: Button = findViewById(R.id.print_log)
		printLog.setOnClickListener {
			CooderLog.i("第一条", "第二条", "第三条", "第四条")
			CooderLog.e("这个是一条日志信息")
			
			CooderLog.log(object : CooderLogConfig() {
				override fun includeThread(): Boolean {
					return true
				}
				
				override fun includeStackTrack(): Boolean {
					return true
				}
				
				override fun stackTrackDepth(): Int {
					return 20
				}
			}, CooderLogType.I, "Cooder2", "Hello World!")
		}
	}
}