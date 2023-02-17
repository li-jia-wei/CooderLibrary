package com.cooder.cooder.app.ui.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cooder.cooder.app.R
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
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_test)
		immersiveStatusBar(false)
	}
}