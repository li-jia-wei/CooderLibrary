package com.cooder.cooder.library.app.ui.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cooder.cooder.library.app.R
import com.cooder.cooder.library.app.ui.item.dataitem.DataItem
import com.cooder.cooder.library.app.ui.item.dataitem.ItemData
import com.cooder.cooder.ui.item.CooderAdapter
import com.cooder.cooder.ui.item.CooderDataItem

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
		item()
	}
	
	private fun item() {
		val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
		recyclerView.layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
		val dataItem1 = DataItem(ItemData(this, "1"))
		val dataItem2 = DataItem(ItemData(this, "2"))
		val dataItem3 = DataItem(ItemData(this, "3"))
		val dataItem4 = DataItem(ItemData(this, "4"))
		val dataItem5 = DataItem(ItemData(this, "5"))
		val dataItem6 = DataItem(ItemData(this, "6"))
		val adapter = CooderAdapter(this)
		recyclerView.adapter = adapter
		val dataItems = mutableListOf<CooderDataItem<ItemData, out RecyclerView.ViewHolder>>()
		dataItems += dataItem1
		dataItems += dataItem2
		dataItems += dataItem3
		dataItems += dataItem4
		dataItems += dataItem5
		dataItems += dataItem6
		adapter.addItems(dataItems)
		adapter.refreshAllItems()
		val headerView1 = LayoutInflater.from(this).inflate(R.layout.data_item, window.decorView as ViewGroup, false)
		val headerTv1: TextView = headerView1.findViewById(R.id.tv)
		headerTv1.text = "Header1"
		adapter.addHeaderView(headerView1)
		val headerView2 = LayoutInflater.from(this).inflate(R.layout.data_item, window.decorView as ViewGroup, false)
		val headerTv2: TextView = headerView2.findViewById(R.id.tv)
		headerTv2.text = "Header2"
		adapter.addHeaderView(headerView2)
		val footerView1 = LayoutInflater.from(this).inflate(R.layout.data_item, window.decorView as ViewGroup, false)
		val footerTv1: TextView = footerView1.findViewById(R.id.tv)
		footerTv1.text = "Footer1"
		adapter.addFooterView(footerView1)
		val footerView2 = LayoutInflater.from(this).inflate(R.layout.data_item, window.decorView as ViewGroup, false)
		val footerTv2: TextView = footerView2.findViewById(R.id.tv)
		footerTv2.text = "Footer2"
		adapter.addFooterView(footerView2)
	}
}