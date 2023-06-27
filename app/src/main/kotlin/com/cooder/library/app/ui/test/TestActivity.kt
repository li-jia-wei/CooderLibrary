package com.cooder.library.app.ui.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cooder.library.app.databinding.ActivityTestBinding
import com.cooder.library.library.util.expends.immersiveStatusBar

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
	}
}