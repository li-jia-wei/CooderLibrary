package com.cooder.cooder.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.cooder.cooder.app.R
import com.cooder.cooder.app.databinding.ActivityMainBinding
import com.cooder.cooder.app.ui.banner.CoBannerActivity
import com.cooder.cooder.app.ui.cache.CoCacheActivity
import com.cooder.cooder.app.ui.databus.CoDataBusActivity
import com.cooder.cooder.app.ui.item.CoDataItemActivity
import com.cooder.cooder.app.ui.log.CoLogActivity
import com.cooder.cooder.app.ui.refresh.CoRefreshActivity
import com.cooder.cooder.app.ui.slider.CoSliderActivity
import com.cooder.cooder.app.ui.tab.CoTabBottomActivity
import com.cooder.cooder.app.ui.tab.CoTabTopActivity
import com.cooder.cooder.app.ui.test.TestActivity
import com.cooder.cooder.ui.item.CoAdapter
import com.cooder.cooder.ui.item.CoDataItem
import com.cooder.cooder.ui.item.CoViewHolder
import kotlin.reflect.KClass

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
	
	private lateinit var binding: ActivityMainBinding
	
	private val components: List<Pair<KClass<out AppCompatActivity>, String>> = listOf(
		TestActivity::class to "Test",
		CoLogActivity::class to "Log",
		CoTabBottomActivity::class to "TabBottom",
		CoTabTopActivity::class to "TabTop",
		CoRefreshActivity::class to "Refresh",
		CoBannerActivity::class to "Banner",
		CoDataItemActivity::class to "DataItem",
		CoSliderActivity::class to "Slider",
		CoDataBusActivity::class to "DataBus",
		CoCacheActivity::class to "Cache"
	)
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
		val adapter = CoAdapter(this)
		binding.recyclerView.adapter = adapter
		val dataItems = mutableListOf<ComponentsDataItem>()
		components.forEach {
			dataItems += ComponentsDataItem(this, it)
		}
		adapter.addItems(dataItems)
	}
	
	class ComponentsDataItem(
		private val context: Context,
		private val itemData: Pair<KClass<out AppCompatActivity>, String>
	) : CoDataItem<Pair<KClass<out AppCompatActivity>, String>, ComponentsHolder>() {
		
		override fun getItemLayoutRes(): Int {
			return R.layout.item_component
		}
		
		override fun getSpanSize(): Int {
			return 1
		}
		
		override fun onBindData(holder: ComponentsHolder, position: Int) {
			holder.name.text = itemData.second
			holder.name.setOnClickListener {
				context.startActivity(Intent(context, itemData.first.java))
			}
		}
	}
	
	class ComponentsHolder(itemView: View) : CoViewHolder(itemView) {
		val name: TextView = findViewById(R.id.name)
	}
}