package com.cooder.cooder.app.ui.item.dataitem

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.app.R
import com.cooder.cooder.ui.item.CooderDataItem

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/7 20:17
 *
 * 介绍：TopTabDataItem
 */
class BannerDataItem(
	itemData: ItemData
) : CooderDataItem<ItemData, RecyclerView.ViewHolder>(itemData) {
	
	override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
		val imageView: ImageView = holder.itemView.findViewById(R.id.iv_image)
		imageView.setImageResource(R.drawable.iv_cooder_data_item_image)
	}
	
	override fun getItemLayoutRes(): Int {
		return R.layout.layout_cooder_data_item_banner
	}
}