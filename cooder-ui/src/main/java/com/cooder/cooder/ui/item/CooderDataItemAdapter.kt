package com.cooder.cooder.ui.item

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/7 09:22
 *
 * 介绍：CooderAdapter
 */
class CooderDataItemAdapter(
	context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
	
	private var inflater = LayoutInflater.from(context)
	private val dataSets = mutableListOf<CooderDataItem<*, out RecyclerView.ViewHolder>>()
	private val typeArray = SparseArray<CooderDataItem<*, out RecyclerView.ViewHolder>>()
	
	fun addItem(item: CooderDataItem<*, RecyclerView.ViewHolder>, notify: Boolean = true, index: Int = -1) {
		if (index >= 0 && index <= dataSets.size) {
			dataSets.add(index, item)
		} else {
			dataSets += item
		}
		if (notify) {
			val notifyPos = if (index >= 0) index else dataSets.lastIndex
			notifyItemInserted(notifyPos)
		}
	}
	
	fun addItems(items: List<CooderDataItem<*, out RecyclerView.ViewHolder>>, notify: Boolean = true) {
		val start = dataSets.size
		dataSets += items
		if (notify) {
			notifyItemRangeInserted(start, items.size)
		}
	}
	
	fun removeItem(index: Int): CooderDataItem<*, out RecyclerView.ViewHolder>? {
		if (index >= 0 && index < dataSets.size) {
			val remove = dataSets.removeAt(index)
			notifyItemRemoved(index)
			return remove
		}
		return null
	}
	
	fun removeItem(item: CooderDataItem<*, out RecyclerView.ViewHolder>?) {
		if (item != null) {
			val index = dataSets.indexOf(item)
			removeItem(index)
		}
	}
	
	fun refreshItem(dataItem: CooderDataItem<*, out RecyclerView.ViewHolder>) {
		val indexOf = dataSets.indexOf(dataItem)
		notifyItemChanged(indexOf)
	}
	
	override fun getItemViewType(position: Int): Int {
		val dataItem = dataSets[position]
		val type = dataItem.javaClass.hashCode()
		// 如果还没有包装这种类型的Item，则添加进来
		if (typeArray.indexOfKey(type) < 0) {
			typeArray[type] = dataItem
		}
		return type
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		val dataItem = typeArray[viewType]
		var view: View? = dataItem.getItemView(parent)
		if (view == null) {
			val layoutRes = dataItem.getItemLayoutRes()
			if (layoutRes < 0) {
				throw RuntimeException("dataItem: ${dataItem.javaClass.name} must override getItemView or getItemLayoutRes method.")
			}
			view = inflater!!.inflate(layoutRes, parent, false)
		}
		return createViewHolderInternal(dataItem.javaClass, view)
	}
	
	private fun createViewHolderInternal(javaClass: Class<CooderDataItem<*, out RecyclerView.ViewHolder>>, view: View?): RecyclerView.ViewHolder {
		val superclass: Type? = javaClass.genericSuperclass
		if (superclass is ParameterizedType) {
			val arguments = superclass.actualTypeArguments
			try {
				for (argument in arguments) {
					if (argument is Class<*> && RecyclerView.ViewHolder::class.java.isAssignableFrom(argument)) {
						return argument.getConstructor(View::class.java).newInstance(view) as RecyclerView.ViewHolder
					}
				}
			} catch (e: Throwable) {
				e.printStackTrace()
			}
		}
		return object : RecyclerView.ViewHolder(view!!) {}
	}
	
	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val dataItem = getItem(position)
		dataItem?.onBindData(holder, position)
	}
	
	private fun getItem(position: Int): CooderDataItem<*, RecyclerView.ViewHolder>? {
		if (position >= 0 && position < dataSets.size) {
			@Suppress("UNCHECKED_CAST")
			return dataSets[position] as CooderDataItem<*, RecyclerView.ViewHolder>
		}
		return null
	}
	
	override fun getItemCount(): Int {
		return dataSets.size
	}
	
	override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
		super.onAttachedToRecyclerView(recyclerView)
		val layoutManager = recyclerView.layoutManager
		if (layoutManager is GridLayoutManager) {
			val spanCount = layoutManager.spanCount
			layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
				override fun getSpanSize(position: Int): Int {
					val dataItem = dataSets[position]
					val spanSize = dataItem.getSpanSize()
					return if (spanSize <= 0) spanCount else spanSize
				}
			}
		}
	}
}