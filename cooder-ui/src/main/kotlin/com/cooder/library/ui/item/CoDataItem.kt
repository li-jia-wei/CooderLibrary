package com.cooder.library.ui.item

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/30 23:28
 *
 * 介绍：CoDataItem，配合CoAdapter使用
 */
abstract class CoDataItem<DATA, VM : CoViewHolder> {
	
	private var adapter: CoAdapter? = null
	
	/**
	 * 绑定数据
	 */
	abstract fun onBindData(holder: VM, position: Int)
	
	/**
	 * 返回该Item的布局资源ID
	 */
	@LayoutRes
	open fun getItemLayoutRes(): Int {
		return -1
	}
	
	/**
	 * 返回该Item的视图View，这个比getItemLayoutRes优先
	 */
	open fun getItemView(inflater: LayoutInflater, parent: ViewGroup): View? {
		return null
	}
	
	/**
	 * 设置Adapter
	 */
	fun setAdapter(adapter: CoAdapter) {
		this.adapter = adapter
	}
	
	/**
	 * 刷新Item
	 */
	fun refreshItem() {
		adapter?.refreshItem(this) ?: throw RuntimeException("adapter == null, 请先执行setAdapter方法")
	}
	
	/**
	 * 从列表上移除
	 */
	fun removeItem() {
		adapter?.removeItem(this) ?: throw RuntimeException("adapter == null, 请先执行setAdapter方法")
	}
	
	/**
	 * 设置DataItem占多少列，大于设置的列数则等于设置的列数
	 */
	@IntRange(from = 0, to = 4)
	open fun getSpanSize(): Int {
		return 0
	}
	
	/**
	 * 该Item被滑进屏幕
	 */
	open fun onViewAttachedToWindow(holder: VM) {
	
	}
	
	/**
	 * 该Item被划出屏幕
	 */
	open fun onViewDetachedFromWindow(holder: VM) {
	
	}
	
	open fun getViewHolder(inflater: LayoutInflater, parent: ViewGroup): VM? {
		return null
	}
}