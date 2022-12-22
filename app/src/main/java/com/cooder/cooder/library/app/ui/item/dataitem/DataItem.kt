package com.cooder.cooder.library.app.ui.item.dataitem

import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setMargins
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.library.app.R
import com.cooder.cooder.library.util.dp
import com.cooder.cooder.ui.item.CooderDataItem

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/1 00:39
 *
 * 介绍：OneDataItem
 */
open class DataItem(
	private val itemData: ItemData,
) : CooderDataItem<ItemData, RecyclerView.ViewHolder>(itemData) {
	
	override fun getItemLayoutRes(): Int {
		return R.layout.data_item
	}
	
	override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
		val tv: TextView = holder.itemView.findViewById(R.id.tv)
		tv.text = itemData.name
		val param = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 80.dp.toInt())
		param.setMargins(5.dp.toInt())
		tv.layoutParams = param
	}
	
	override fun getSpanSize(): Int {
		return 1
	}
}