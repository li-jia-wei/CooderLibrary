package com.cooder.cooder.app.ui.item

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.app.R
import com.cooder.cooder.app.ui.item.dataitem.DataItem
import com.cooder.cooder.app.ui.item.dataitem.ItemData
import com.cooder.cooder.library.util.expends.setStatusBar
import com.cooder.cooder.ui.item.CoAdapter

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/7 20:13
 *
 * 介绍：CooderDataItemActivity
 */
class CoDataItemActivity : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_co_data_item)
		setStatusBar(true, Color.WHITE)
		
		item()
	}
	
	private fun item() {
		val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
		recyclerView.layoutManager = GridLayoutManager(this, 2)
		val adapter = CoAdapter(this)
		recyclerView.adapter = adapter
		val bottom1 = createView("BOTTOM")
		adapter.setBottomView(bottom1)
		val items = mutableListOf<DataItem>()
		repeat(30) {
			items += DataItem(ItemData(this, "Item${it + 1}"))
		}
		adapter.addItems(items)
		recyclerView.scrollToPosition(0)
	}
	
	private fun createView(text: String): View {
		val view = LayoutInflater.from(this).inflate(R.layout.data_item, window.decorView as ViewGroup, false)
		val textView: TextView = view.findViewById(R.id.tv)
		textView.text = text
		return view
	}
}