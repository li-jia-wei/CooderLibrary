package com.cooder.library.app.ui.search

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cooder.library.app.databinding.ActivityCoSearchBinding
import com.cooder.library.library.log.CoLog

class CoSearchViewActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityCoSearchBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityCoSearchBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		initSearchView()
	}
	
	
	private fun initSearchView() {
		binding.searchView.setNavListener {
			finish()
		}
		binding.searchView.setSearchListener {
			Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
		}
		binding.searchView.setSearchContentChangeListener {
			CoLog.i("SearchChange: $it")
		}
		binding.searchView.closeHint()
	}
}