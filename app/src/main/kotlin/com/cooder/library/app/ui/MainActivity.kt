package com.cooder.library.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.cooder.library.app.R
import com.cooder.library.app.databinding.ActivityMainBinding
import com.cooder.library.app.ui.banner.CoBannerActivity
import com.cooder.library.app.ui.cache.CoCacheActivity
import com.cooder.library.app.ui.databus.CoDataBusActivity
import com.cooder.library.app.ui.item.CoDataItemActivity
import com.cooder.library.app.ui.log.CoLogActivity
import com.cooder.library.app.ui.navigation.CoNavigationBarActivity
import com.cooder.library.app.ui.refresh.CoRefreshActivity
import com.cooder.library.app.ui.search.CoSearchViewActivity
import com.cooder.library.app.ui.slider.CoSliderActivity
import com.cooder.library.app.ui.tab.CoTabBottomActivity
import com.cooder.library.app.ui.tab.CoTabTopActivity
import com.cooder.library.app.ui.test.TestActivity
import com.cooder.library.ui.item.CoAdapter
import com.cooder.library.ui.item.CoDataItem
import com.cooder.library.ui.item.CoViewHolder
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
		CoLogActivity::class to "CoLog",
		CoTabBottomActivity::class to "CoTabBottom",
		CoTabTopActivity::class to "CoTabTop",
		CoRefreshActivity::class to "CoRefresh",
		CoBannerActivity::class to "CoBanner",
		CoDataItemActivity::class to "CoDataItem",
		CoSliderActivity::class to "CoSlider",
		CoDataBusActivity::class to "CoDataBus",
		CoCacheActivity::class to "CoCache",
		CoNavigationBarActivity::class to "CoNavigationBar",
		CoSearchViewActivity::class to "CoSearchView"
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