package com.cooder.library.app.ui.amount

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cooder.library.app.databinding.ActivityCoAmountViewBinding
import com.cooder.library.library.log.CoLog

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/12 23:20
 *
 * 介绍：CoAmountViewActivity
 */
class CoAmountViewActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityCoAmountViewBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityCoAmountViewBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		binding.amountView.setAmountChangeListener {
			CoLog.i(it)
		}
	}
}