package com.cooder.cooder.app.ui

import android.os.Bundle
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.cooder.cooder.app.R
import com.cooder.cooder.app.adapter.ComponentsAdapter
import com.cooder.cooder.app.mo.ComponentMo
import com.cooder.cooder.app.ui.log.CooderLogActivity
import com.cooder.cooder.app.ui.refresh.CooderRefreshActivity
import com.cooder.cooder.app.ui.tab.CooderTabBottomActivity
import com.cooder.cooder.app.ui.tab.CooderTabTopActivity

class MainActivity : AppCompatActivity() {
	
	private val components = listOf(
		ComponentMo(CooderLogActivity::class.java, "Log"),
		ComponentMo(CooderTabBottomActivity::class.java, "TabBottom"),
		ComponentMo(CooderTabTopActivity::class.java, "TabTop"),
		ComponentMo(CooderRefreshActivity::class.java, "Refresh")
	)
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		
		val gridView: GridView = findViewById(R.id.grid_view)
		gridView.adapter = ComponentsAdapter(this, components)
		
	}
}