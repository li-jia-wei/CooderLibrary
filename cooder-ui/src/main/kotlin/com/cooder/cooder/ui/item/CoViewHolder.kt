package com.cooder.cooder.ui.item

import android.content.Context
import android.util.SparseArray
import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/1/12 19:11
 *
 * 介绍：CoViewHolder
 */
open class CoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
	
	private val viewCaches = SparseArray<View>()
	
	val context: Context get() = itemView.context
	
	/**
	 * 查找View
	 */
	@Suppress("UNCHECKED_CAST")
	fun <T : View> findViewById(@IdRes id: Int): T {
		var view = viewCaches[id]
		if (view == null) {
			view = itemView.findViewById(id)
			viewCaches[id] = view
		}
		if (view == null) {
			throw IllegalStateException("This view is null.")
		}
		return view as T
	}
}