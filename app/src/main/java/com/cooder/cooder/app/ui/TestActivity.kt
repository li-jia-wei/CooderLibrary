package com.cooder.cooder.app.ui

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.cooder.cooder.app.R
import com.cooder.cooder.app.coroutine.SemaphoreDemo

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
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_test)
		
		val test = findViewById<Button>(R.id.test)
		test.setOnClickListener {
			SemaphoreDemo().start()
		}
	}
}