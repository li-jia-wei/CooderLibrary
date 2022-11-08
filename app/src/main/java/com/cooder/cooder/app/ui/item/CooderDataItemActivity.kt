package com.cooder.cooder.app.ui.item

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.app.R
import com.cooder.cooder.app.ui.item.dataitem.BannerDataItem
import com.cooder.cooder.app.ui.item.dataitem.GridDataItem
import com.cooder.cooder.app.ui.item.dataitem.ItemData
import com.cooder.cooder.app.ui.item.dataitem.TopTabDataItem
import com.cooder.cooder.ui.item.CooderDataItem
import com.cooder.cooder.ui.item.CooderDataItemAdapter

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/7 20:13
 *
 * 介绍：CooderDataItemActivity
 */
class CooderDataItemActivity : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_cooder_data_item)
		val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
		recyclerView.layoutManager = GridLayoutManager(this, 2)
		val adapter = CooderDataItemAdapter(this)
		recyclerView.adapter = adapter
		val dataSets = mutableListOf<CooderDataItem<*, out RecyclerView.ViewHolder>>()
		dataSets += TopTabDataItem(ItemData())
		dataSets += BannerDataItem(ItemData())
		dataSets += GridDataItem(ItemData())
		adapter.addItems(dataSets.toList(), true)
	}
}