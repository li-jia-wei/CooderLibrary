package com.cooder.cooder.app.ui.test

import android.content.Intent
import android.os.Bundle
import android.os.Process
import androidx.appcompat.app.AppCompatActivity
import com.cooder.cooder.app.databinding.ActivityTestBinding
import com.cooder.cooder.library.util.expends.immersiveStatusBar

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/27 17:00
 *
 * 介绍：TestActivity
 */
class TestActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityTestBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityTestBinding.inflate(layoutInflater)
		setContentView(binding.root)
		immersiveStatusBar(false)
		
		binding.btn.setOnClickListener {
			val intent = packageManager.getLaunchIntentForPackage(packageName)
			intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
			startActivity(intent)
			// 把自己干掉
			Process.killProcess(Process.myPid())
		}
	}
}