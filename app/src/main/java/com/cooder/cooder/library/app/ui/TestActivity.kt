package com.cooder.cooder.library.app.ui

import android.os.Bundle
import android.util.SparseArray
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.cooder.cooder.library.app.R
import com.cooder.cooder.library.log.CooderLog

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
	
	private val sparseArray = SparseArray<String>()
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_test)
		
		val test = findViewById<Button>(R.id.test)
		test.setOnClickListener {
			sparseArray[100] = "Hello"
			CooderLog.i(sparseArray.indexOfValue("Hello"))
		}
	}
}