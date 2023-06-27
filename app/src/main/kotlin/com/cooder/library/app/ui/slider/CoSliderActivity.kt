package com.cooder.library.app.ui.slider

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cooder.library.app.databinding.ActivityCoSliderBinding
import com.cooder.library.library.util.expends.setStatusBar

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/24 16:05
 *
 * 介绍：CooderSliderActivity
 */
class CoSliderActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityCoSliderBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityCoSliderBinding.inflate(layoutInflater)
		setContentView(binding.root)
		setStatusBar(true, Color.WHITE)
	}
}