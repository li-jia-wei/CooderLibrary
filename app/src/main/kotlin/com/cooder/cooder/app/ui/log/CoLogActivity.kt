package com.cooder.cooder.app.ui.log

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cooder.cooder.app.databinding.ActivityCoLogBinding
import com.cooder.cooder.library.log.CoLog
import com.cooder.cooder.library.log.CoLogConfig
import com.cooder.cooder.library.log.CoLogManager
import com.cooder.cooder.library.log.CoLogType
import com.cooder.cooder.library.log.printer.CoViewPrinter
import com.cooder.cooder.library.util.expends.immersiveStatusBar

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/24 00:34
 *
 * 介绍：日志Demo
 */
class CoLogActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityCoLogBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityCoLogBinding.inflate(layoutInflater)
		setContentView(binding.root)
		immersiveStatusBar(true)
		
		val viewPrinter = CoViewPrinter(this)
		viewPrinter.getViewProvider().showFloatingView()
		CoLogManager.getInstance().addPrinter(viewPrinter)
		
		binding.printLog.setOnClickListener {
			CoLog.i("第一条", "第二条", "第三条", "第四条")
			CoLog.e("这个是一条日志信息")
			
			CoLog.log(object : CoLogConfig() {
				override fun includeThread(): Boolean {
					return true
				}
				
				override fun includeStackTrack(): Boolean {
					return true
				}
				
				override fun stackTrackDepth(): Int {
					return 20
				}
			}, CoLogType.I, "Cooder2", "Hello World!")
		}
	}
}