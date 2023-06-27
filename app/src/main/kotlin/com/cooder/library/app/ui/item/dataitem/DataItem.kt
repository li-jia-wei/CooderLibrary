package com.cooder.library.app.ui.item.dataitem

import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setMargins
import com.cooder.library.app.R
import com.cooder.library.library.util.expends.dpInt
import com.cooder.library.ui.item.CoDataItem
import com.cooder.library.ui.item.CoViewHolder

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
) : CoDataItem<ItemData, CoViewHolder>() {
	
	override fun getItemLayoutRes(): Int {
		return R.layout.data_item
	}
	
	override fun onBindData(holder: CoViewHolder, position: Int) {
		val tv: TextView = holder.itemView.findViewById(R.id.tv)
		tv.text = itemData.name
		val param = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 80.dpInt)
		param.setMargins(5.dpInt)
		tv.layoutParams = param
	}
	
	override fun getSpanSize(): Int {
		return 1
	}
}