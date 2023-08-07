package com.cooder.library.app.ui.log

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cooder.library.app.databinding.ActivityCoLogBinding
import com.cooder.library.library.log.CoLog
import com.cooder.library.library.log.CoLogLevel
import com.cooder.library.library.log.CoLogManager
import com.cooder.library.library.log.config.CoLogConfig
import com.cooder.library.library.log.printer.CoViewPrinter
import com.cooder.library.library.util.expends.immersiveStatusBar

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
				override val enable: Boolean = true
				override val includeStackTrack: Boolean = true
				override val stackTrackDepth: Int = 20
			}, CoLogLevel.INFO, "CustomCooder", "Hello World!")
		}
	}
}