package com.cooder.cooder.app.ui

import android.content.Intent
import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.cooder.cooder.app.R
import com.cooder.cooder.app.adapter.ComponentsAdapter
import com.cooder.cooder.app.ui.banner.CooderBannerActivity
import com.cooder.cooder.app.ui.item.CooderDataItemActivity
import com.cooder.cooder.app.ui.log.CooderLogActivity
import com.cooder.cooder.app.ui.refresh.CooderRefreshActivity
import com.cooder.cooder.app.ui.tab.CooderTabBottomActivity
import com.cooder.cooder.app.ui.tab.CooderTabTopActivity

class MainActivity : AppCompatActivity() {
	
	private val isTestMode = false
	
	private val components = listOf(
		CooderLogActivity::class.java to "Log",
		CooderTabBottomActivity::class.java to "TabBottom",
		CooderTabTopActivity::class.java to "TabTop",
		CooderRefreshActivity::class.java to "Refresh",
		CooderBannerActivity::class.java to "Banner",
		CooderDataItemActivity::class.java to "DataItem"
	)
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		if (isTestMode) {
			startActivity(Intent(this, TestActivity::class.java))
			finish()
		}
		setContentView(R.layout.activity_main)
		
		val gridView: GridView = findViewById(R.id.grid_view)
		gridView.adapter = ComponentsAdapter(this, components)
	}
}