package com.cooder.library.app.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cooder.library.app.databinding.ActivityCoSearchBinding
import com.cooder.library.library.log.CoLog

class CoSearchViewActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityCoSearchBinding
	
	private companion object {
		private var LAST = ""
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityCoSearchBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		binding.searchView.setNavListener {
			finish()
		}
		
		binding.searchView.setSearchListener {
			LAST = it
			CoLog.i("搜索内容：$it")
		}
		
		binding.searchView.setHistorySearch(LAST)
	}
}