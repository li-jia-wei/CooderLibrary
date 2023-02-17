package com.cooder.cooder.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.app.R
import com.cooder.cooder.app.ui.banner.CoBannerActivity
import com.cooder.cooder.app.ui.item.CoDataItemActivity
import com.cooder.cooder.app.ui.log.CoLogActivity
import com.cooder.cooder.app.ui.refresh.CoRefreshActivity
import com.cooder.cooder.app.ui.slider.CoSliderActivity
import com.cooder.cooder.app.ui.tab.CoTabBottomActivity
import com.cooder.cooder.app.ui.tab.CoTabTopActivity
import com.cooder.cooder.app.ui.test.TestActivity
import com.cooder.cooder.ui.item.CoAdapter
import com.cooder.cooder.ui.item.CoDataItem
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
	
	private val components: List<Pair<KClass<out AppCompatActivity>, String>> = listOf(
		TestActivity::class to "Test",
		CoLogActivity::class to "Log",
		CoTabBottomActivity::class to "TabBottom",
		CoTabTopActivity::class to "TabTop",
		CoRefreshActivity::class to "Refresh",
		CoBannerActivity::class to "Banner",
		CoDataItemActivity::class to "DataItem",
		CoSliderActivity::class to "Slider"
	)
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
		recyclerView.layoutManager = GridLayoutManager(this, 2)
		val adapter = CoAdapter(this)
		recyclerView.adapter = adapter
		val dataItems = mutableListOf<ComponentsDataItem>()
		components.forEach {
			dataItems += ComponentsDataItem(this, it)
		}
		adapter.addItems(dataItems)
	}
	
	class ComponentsDataItem(
		private val context: Context, private val itemData: Pair<KClass<out AppCompatActivity>, String>
	) : CoDataItem<Pair<KClass<out AppCompatActivity>, String>, ComponentsHolder>(itemData) {
		
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
	
	class ComponentsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val name: TextView = itemView.findViewById(R.id.name)
	}
}