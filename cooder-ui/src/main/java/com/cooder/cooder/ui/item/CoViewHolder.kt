package com.cooder.cooder.ui.item

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
	
	private val viewCache = SparseArray<View>()
	
	/**
	 * 查找View
	 */
	fun <T : View> findViewById(@IdRes id: Int): T? {
		var view = viewCache[id]
		if (view == null) {
			view = itemView.findViewById(id)
            viewCache[id] = view
        }
        @Suppress("UNCHECKED_CAST")
        return view as? T
    }
}