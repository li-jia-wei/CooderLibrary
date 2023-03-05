package com.cooder.cooder.app.ui.databus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cooder.cooder.app.databinding.ActivityCoDataBusBinding
import com.cooder.cooder.library.util.CoDataBus

class CoDataBusActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityCoDataBusBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityCoDataBusBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		CoDataBus.with<String>("stickyData").setStickyData("先发送的粘性事件")
		
		binding.testDataBus.setOnClickListener {
			CoDataBus.with<String>("stickyData").observeSticky(this) {
			
			}
		}
	}
}