package com.cooder.cooder.ui.item

import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/7 09:14
 *
 * 介绍：CooderDataItem
 */
abstract class CooderDataItem<DATA, VM : RecyclerView.ViewHolder>(
	private val data: DATA
) {
	
	private var adapter: CooderDataItemAdapter? = null
	
	abstract fun onBindData(holder: VM, position: Int)
	
	/**
	 * 返回该Item的布局资源ID
	 */
	@LayoutRes
	open fun getItemLayoutRes(): Int {
		return -1
	}
	
	/**
	 * 返回该Item的视图View
	 */
	open fun getItemView(parent: ViewGroup): View? {
		return null
	}
	
	fun setAdapter(adapter: CooderDataItemAdapter) {
		this.adapter = adapter
	}
	
	/**
	 * 刷新列表
	 */
	fun refreshItem() {
		adapter?.refreshItem(this) ?: throw RuntimeException("Please setAdapter before refreshItem.")
	}
	
	/**
	 * 从列表上移除
	 */
	fun removeItem() {
		adapter?.removeItem(this) ?: throw RuntimeException("Please setAdapter before removeItem.")
	}
	
	/**
	 * 获取多少列
	 */
	open fun getSpanSize(): Int {
		return 0
	}
}