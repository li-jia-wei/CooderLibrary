package com.cooder.cooder.app.ui.item

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.cooder.cooder.app.R
import com.cooder.cooder.app.databinding.ActivityCoDataItemBinding
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
	
	private lateinit var binding: ActivityCoDataItemBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityCoDataItemBinding.inflate(layoutInflater)
		setContentView(binding.root)
		setStatusBar(true, Color.WHITE)
		
		binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
		val adapter = CoAdapter(this)
		binding.recyclerView.adapter = adapter
		val bottom1 = createView("BOTTOM")
		adapter.setBottomView(bottom1)
		adapter.addItem(DataItem(ItemData(this, "Item0")))
		val items = mutableListOf<DataItem>()
		repeat(30) {
			items += DataItem(ItemData(this, "Item${it + 1}"))
		}
		adapter.addItems(items)
		binding.recyclerView.scrollToPosition(0)
//		adapter.removeAll()
	}
	
	private fun createView(text: String): View {
		val view = LayoutInflater.from(this).inflate(R.layout.data_item, window.decorView as ViewGroup, false)
		val textView: TextView = view.findViewById(R.id.tv)
		textView.text = text
		return view
	}
}