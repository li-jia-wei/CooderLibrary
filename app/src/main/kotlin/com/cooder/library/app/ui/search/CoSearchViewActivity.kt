package com.cooder.library.app.ui.search

import android.os.Bundle
import android.widget.Toast
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
		binding.searchView.setHistorySearchContent(LAST)
		binding.searchView.setSearchListener {
			LAST = it
			Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
		}
		binding.searchView.setEditTextChangeListener {
			CoLog.i("SearchChange: $it")
		}
	}
}