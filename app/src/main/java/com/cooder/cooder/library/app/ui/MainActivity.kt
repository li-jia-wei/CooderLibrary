package com.cooder.cooder.library.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.library.app.R
import com.cooder.cooder.library.app.ui.banner.CooderBannerActivity
import com.cooder.cooder.library.app.ui.item.CooderDataItemActivity
import com.cooder.cooder.library.app.ui.log.CooderLogActivity
import com.cooder.cooder.library.app.ui.refresh.CooderRefreshActivity
import com.cooder.cooder.library.app.ui.tab.CooderTabBottomActivity
import com.cooder.cooder.library.app.ui.tab.CooderTabTopActivity
import com.cooder.cooder.ui.item.CooderAdapter
import com.cooder.cooder.ui.item.CooderDataItem

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/24 00:34
 *
 * 介绍：MainActivity
 */
class MainActivity : AppCompatActivity() {
	
	private val isTest = false
	
	private val components: List<Pair<Class<out AppCompatActivity>, String>> = listOf(
		CooderLogActivity::class.java to "Log",
		CooderTabBottomActivity::class.java to "TabBottom",
		CooderTabTopActivity::class.java to "TabTop",
		CooderRefreshActivity::class.java to "Refresh",
		CooderBannerActivity::class.java to "Banner",
		CooderDataItemActivity::class.java to "DataItem"
	)
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		if (isTest) {
			startActivity(Intent(this, TestActivity::class.java))
			finish()
		}
		setContentView(R.layout.activity_main)
		
		val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
		recyclerView.layoutManager = GridLayoutManager(this, 2)
		val adapter = CooderAdapter(this)
		recyclerView.adapter = adapter
		components.forEach {
			adapter.addItem(ComponentsDataItem(this, it))
		}
	}
	
	class ComponentsDataItem(
		private val context: Context,
		private val itemData: Pair<Class<out AppCompatActivity>, String>
	) : CooderDataItem<Pair<Class<out AppCompatActivity>, String>, ComponentsHolder>(itemData) {
		
		override fun getItemLayoutRes(): Int {
			return R.layout.item_component
		}
		
		override fun getSpanSize(): Int {
			return 1
		}
		
		override fun onBindData(holder: ComponentsHolder, position: Int) {
			holder.name.text = itemData.second
			holder.name.setOnClickListener {
				context.startActivity(Intent(context, itemData.first))
			}
		}
	}
	
	class ComponentsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val name: TextView = itemView.findViewById(R.id.name)
	}
}