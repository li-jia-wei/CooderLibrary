package com.cooder.cooder.ui.item

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.containsKey
import androidx.core.util.containsValue
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType
import kotlin.math.min

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/30 23:27
 *
 * 介绍：作为RecyclerView的适配器，适配GridLayout、StaggeredGridLayout
 */
class CooderAdapter(
	context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
	
	private val inflater = LayoutInflater.from(context)
	private val dataItems = mutableListOf<CooderDataItem<*, out RecyclerView.ViewHolder>>()
	private val typeArray = SparseArray<CooderDataItem<*, out RecyclerView.ViewHolder>>()
	private var recyclerViewRef: WeakReference<RecyclerView>? = null
	
	private val headers = SparseArray<View>()
	private val footers = SparseArray<View>()
	
	private companion object {
		private var BASE_HEADER_ITEM = 1000000
		private var BASE_FOOTER_ITEM = 2000000
	}
	
	/**
	 * 添加一个DataItem
	 * @param dataItem DataItem
	 * @param notify 是否刷新
	 * @param index 在DataItem中插入的位置
	 */
	@JvmOverloads
	fun addItem(dataItem: CooderDataItem<*, out RecyclerView.ViewHolder>, notify: Boolean = true, index: Int = -1) {
		val isSetIndex = index >= 0 && index <= getItemSize()
		if (isSetIndex) {
			this.dataItems.add(index, dataItem)
		} else {
			this.dataItems += dataItem
		}
		if (notify) {
			if (isSetIndex) {
				notifyItemInserted(getHeaderSize() + index)
			} else {
				notifyItemInserted(getHeaderSize() + getItemSize() - 1)
			}
		}
	}
	
	/**
	 * 添加多个DataItem
	 * @param dataItems 多个DataItem
	 * @param notify 是否刷新
	 * @param startIndex 在DataItem中插入的位置
	 */
	@JvmOverloads
	fun addItems(dataItems: Collection<CooderDataItem<*, out RecyclerView.ViewHolder>>, notify: Boolean = true, startIndex: Int = -1) {
		val isSetStartIndex = startIndex >= 0 && startIndex <= getItemSize()
		if (isSetStartIndex) {
			this.dataItems.addAll(startIndex, dataItems)
		} else {
			this.dataItems += dataItems
		}
		if (notify) {
			if (isSetStartIndex) {
				notifyItemRangeInserted(getHeaderSize() + startIndex, dataItems.size)
			} else {
				notifyItemRangeInserted(getHeaderSize() + getItemSize() - dataItems.size, dataItems.size)
			}
		}
	}
	
	/**
	 * 删除Item
	 * @param dataItem DataItem
	 */
	fun removeItem(dataItem: CooderDataItem<*, out RecyclerView.ViewHolder>?) {
		val index = this.dataItems.indexOf(dataItem)
		if (index >= 0) {
			this.dataItems.removeAt(index)
			notifyItemRemoved(getHeaderSize() + index)
		}
	}
	
	/**
	 * 删除多个Item
	 * @param dataItems 多个DataItem
	 */
	fun removeItems(dataItems: Collection<CooderDataItem<*, out RecyclerView.ViewHolder>?>?) {
		dataItems?.forEach { dataItem ->
			val index = this.dataItems.indexOf(dataItem)
			if (index >= 0) {
				this.dataItems.removeAt(index)
				notifyItemRemoved(getHeaderSize() + index)
			}
		}
	}
	
	/**
	 * 通过索引删除DataItem
	 * @param index 在DataItem中的位置
	 * @return 获取被删除的DataItem
	 */
	fun removeItemAt(index: Int): CooderDataItem<*, out RecyclerView.ViewHolder>? {
		if (index >= 0 && index < getItemSize()) {
			val remove = dataItems.removeAt(index)
			notifyItemRemoved(getHeaderSize() + index)
			return remove
		}
		return null
	}
	
	/**
	 * 通过索引删除多个DataItem
	 * @param startIndex 在DataItem中的开始位置
	 * @param itemCount DataItem的数量
	 * @return 获取被删除的多个DataItem
	 */
	fun removeItemsAt(startIndex: Int, itemCount: Int): Collection<CooderDataItem<*, out RecyclerView.ViewHolder>> {
		val items = mutableListOf<CooderDataItem<*, out RecyclerView.ViewHolder>>()
		if (startIndex >= 0 && startIndex < getItemSize()) {
			val removeCount = min(getItemSize() - startIndex, itemCount)
			repeat(removeCount) {
				val item = dataItems.removeAt(startIndex)
				items += item
			}
			notifyItemRangeRemoved(getHeaderSize() + startIndex, removeCount)
		}
		return items
	}
	
	/**
	 * 删除所有DataItem
	 */
	fun removeAllItems() {
		notifyItemRangeRemoved(getHeaderSize(), getItemSize())
		this.dataItems.clear()
	}
	
	/**
	 * 刷新DataItem
	 * @param dataItem DataItem
	 */
	fun refreshItem(dataItem: CooderDataItem<*, out RecyclerView.ViewHolder>) {
		val index = dataItems.indexOf(dataItem)
		if (index >= 0 && index < getItemSize()) {
			notifyItemChanged(getHeaderSize() + index)
		}
	}
	
	/**
	 * 刷新多个Item
	 * @param dataItems 多个DataItem
	 */
	fun refreshItems(dataItems: Collection<CooderDataItem<*, out RecyclerView.ViewHolder>>) {
		for (dataItem in dataItems) {
			refreshItem(dataItem)
		}
	}
	
	/**
	 * 通过索引刷新DataItem
	 * @param index 在DataItem中的位置
	 */
	fun refreshItemAt(index: Int) {
		if (index >= 0 && index < getItemSize()) {
			notifyItemChanged(getHeaderSize() + index)
		}
	}
	
	/**
	 * 刷新多个DataItemItem
	 * @param startIndex 在DataItem中的开始位置
	 * @param itemCount DataItem的数量
	 */
	fun refreshItemsAt(startIndex: Int, itemCount: Int) {
		val refreshCount = min(getItemSize() - startIndex, itemCount)
		notifyItemRangeChanged(getHeaderSize() + startIndex, refreshCount)
	}
	
	/**
	 * 刷新所有的DataItem
	 */
	fun refreshAllItems() {
		notifyItemRangeChanged(getHeaderSize(), getItemSize() + 1)
	}
	
	/**
	 * 添加HeaderView
	 * @param view HeaderView
	 */
	fun addHeaderView(view: View) {
		if (!headers.containsValue(view)) {
			headers.put(BASE_HEADER_ITEM++, view)
			notifyItemInserted(getHeaderSize() - 1)
		}
	}
	
	/**
	 * 添加多个HeaderView
	 * @param views 多个HeaderView
	 */
	fun addHeaderViews(views: Collection<View>) {
		for (view in views) {
			addHeaderView(view)
		}
	}
	
	/**
	 * 删除HeaderView
	 * @param view HeaderView
	 */
	fun removeHeaderView(view: View) {
		val index = headers.indexOfValue(view)
		if (index >= 0) {
			headers.removeAt(index)
			notifyItemRemoved(index)
		}
	}
	
	/**
	 * 删除多个HeaderView
	 * @param views 多个HeaderView
	 */
	fun removeHeaderViews(views: Collection<View>) {
		for (view in views) {
			removeHeaderView(view)
		}
	}
	
	/**
	 * 删除所有HeaderView
	 */
	fun removeAllHeaderViews() {
		notifyItemRangeRemoved(0, getHeaderSize())
		headers.clear()
	}
	
	/**
	 * 添加FooterView
	 * @param view FooterView
	 */
	fun addFooterView(view: View) {
		if (!footers.containsValue(view)) {
			footers.put(BASE_FOOTER_ITEM++, view)
			notifyItemInserted(itemCount - 1)
		}
	}
	
	/**
	 * 添加多个FooterView
	 * @param views 多个FooterView
	 */
	fun addFooterViews(views: Collection<View>) {
		for (view in views) {
			addFooterView(view)
		}
	}
	
	/**
	 * 删除FooterView
	 * @param view FooterView
	 */
	fun removeFooterView(view: View) {
		val index = footers.indexOfValue(view)
		if (index >= 0 && index < getFooterSize()) {
			footers.removeAt(index)
			notifyItemRemoved(getHeaderSize() + getItemSize() + index)
		}
	}
	
	/**
	 * 删除多个FooterView
	 * @param views 多个FooterView
	 */
	fun removeFooterViews(views: Collection<View>) {
		for (view in views) {
			removeFooterView(view)
		}
	}
	
	/**
	 * 删除所有FooterView
	 */
	fun removeAllFooterView() {
		notifyItemRangeRemoved(getHeaderSize() + getItemSize(), getFooterSize())
		footers.clear()
	}
	
	/**
	 * 清楚所有HeaderView、DataItem和FooterView
	 */
	fun removeAll() {
		notifyItemRangeRemoved(0, itemCount)
		headers.clear()
		dataItems.clear()
		footers.clear()
	}
	
	/**
	 * 获取ItemView类型
	 */
	override fun getItemViewType(position: Int): Int {
		if (isHeaderPosition(position)) {
			return headers.keyAt(position)
		}
		if (isFooterPosition(position)) {
			val footerPosition = getRealFooterPosition(position)
			return footers.keyAt(footerPosition)
		}
		val itemPosition = getRealItemPosition(position)
		val dataItem = dataItems[itemPosition]
		val type = dataItem.javaClass.hashCode()
		if (!typeArray.containsKey(type)) {
			typeArray[type] = dataItem
		}
		return type
	}
	
	/**
	 * 判断是否是Header
	 */
	private fun isHeaderPosition(position: Int): Boolean {
		return position >= 0 && position < getHeaderSize()
	}
	
	/**
	 * 判断是否是Footer
	 */
	private fun isFooterPosition(position: Int): Boolean {
		return position >= getHeaderSize() + getItemSize() && position < itemCount
	}
	
	/**
	 * 创建ViewHolder
	 */
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		if (headers.containsKey(viewType)) {
			val view = headers[viewType]
			return object : RecyclerView.ViewHolder(view) {}
		}
		if (footers.containsKey(viewType)) {
			val view = footers[viewType]
			return object : RecyclerView.ViewHolder(view) {}
		}
		val dataItem = typeArray[viewType]
		var view = dataItem.getItemView(parent)
		val layoutRes = dataItem.getItemLayoutRes()
		if (view != null && layoutRes != -1) {
			throw IllegalStateException("${dataItem.javaClass.name}: getItemView与getItemLayout只能重写一个")
		}
		if (view == null) {
			if (layoutRes == -1) throw RuntimeException("dataItem: ${dataItem.javaClass.name}: 必须重写getItemView或者getItemLayoutRes方法中的一个")
			view = inflater.inflate(layoutRes, parent, false)
		}
		return createViewHolderInternal(dataItem.javaClass, view!!)
	}
	
	/**
	 * 创建ViewHolder
	 */
	private fun createViewHolderInternal(javaClass: Class<CooderDataItem<*, out RecyclerView.ViewHolder>>, view: View): RecyclerView.ViewHolder {
		val superclass = javaClass.genericSuperclass
		if (superclass is ParameterizedType) {
			val arguments = superclass.actualTypeArguments
			for (argument in arguments) {
				if (argument is Class<*> && RecyclerView.ViewHolder::class.java.isAssignableFrom(argument)) {
					try {
						return argument.getConstructor(View::class.java).newInstance(view) as RecyclerView.ViewHolder
					} catch (e: Exception) {
						e.printStackTrace()
					}
				}
			}
		}
		return object : RecyclerView.ViewHolder(view) {}
	}
	
	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		if (isHeaderPosition(position) || isFooterPosition(position)) {
			return
		}
		val itemPosition = getRealItemPosition(position)
		val dataItem = getItem(itemPosition)
		dataItem?.onBindData(holder, position)
	}
	
	private fun getItem(position: Int): CooderDataItem<*, RecyclerView.ViewHolder>? {
		if (position < 0 || position >= getItemSize()) {
			return null
		}
		@Suppress("UNCHECKED_CAST")
		return dataItems[position] as CooderDataItem<*, RecyclerView.ViewHolder>
	}
	
	/**
	 * 获取DataItem的真实索引位置
	 */
	private fun getRealItemPosition(position: Int): Int {
		return position - getHeaderSize()
	}
	
	/**
	 * 获取Footer的真实索引位置
	 */
	private fun getRealFooterPosition(position: Int): Int {
		return position - getHeaderSize() - getItemSize()
	}
	
	/**
	 * 获取Header的数量
	 */
	fun getHeaderSize(): Int {
		return this.headers.size()
	}
	
	/**
	 * 获取DataItem的数量
	 */
	fun getItemSize(): Int {
		return this.dataItems.size
	}
	
	/**
	 * 获取Footer的数量
	 */
	fun getFooterSize(): Int {
		return this.footers.size()
	}
	
	/**
	 * 获取Item总数
	 */
	override fun getItemCount(): Int {
		return this.headers.size() + dataItems.size + footers.size()
	}
	
	/**
	 * 由回收器视图在开始观察此适配器时调用
	 */
	override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
		super.onAttachedToRecyclerView(recyclerView)
		this.recyclerViewRef = WeakReference(recyclerView)
		val manager = recyclerView.layoutManager
		// 适配GridLayout
		if (manager is GridLayoutManager) {
			val spanCount = manager.spanCount
			manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
				override fun getSpanSize(position: Int): Int {
					if (position < itemCount) {
						if (isHeaderPosition(position) || isFooterPosition(position)) {
							return spanCount
						}
						val itemPosition = getRealItemPosition(position)
						val dataItem = dataItems[itemPosition]
						val spanSize = dataItem.getSpanSize()
						return if (spanSize <= 0 || spanSize > spanCount) spanCount else spanSize
					}
					return spanCount
				}
			}
		}
	}
	
	/**
	 * 由回收器视图调用，当它停止观察此适配器
	 */
	override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
		super.onDetachedFromRecyclerView(recyclerView)
		recyclerViewRef?.clear()
	}
	
	/**
	 * 获取AttachRecyclerView
	 */
	fun getAttachRecyclerView(): RecyclerView? {
		return recyclerViewRef?.get()
	}
	
	/**
	 * 当此适配器创建的视图已附加到窗口时调用
	 */
	override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
		super.onViewAttachedToWindow(holder)
		val recyclerView = getAttachRecyclerView()
		if (recyclerView != null) {
			val position = recyclerView.getChildAdapterPosition(holder.itemView)
			val lp = holder.itemView.layoutParams
			// 适配StaggeredGridLayout
			if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
				if (isHeaderPosition(position) || isFooterPosition(position)) {
					lp.isFullSpan = true
					return
				}
				val itemPosition = getRealItemPosition(position)
				val dataItem = getItem(itemPosition) ?: return
				
				val manager = recyclerView.layoutManager as StaggeredGridLayoutManager
				val spanSize = dataItem.getSpanSize()
				if (spanSize == manager.spanCount) {
					lp.isFullSpan = true
				}
				dataItem.onViewAttachedToWindow(holder)
			}
		}
	}
	
	/**
	 * 当此适配器创建的视图已与其窗口分离时调用
	 */
	override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
		super.onViewDetachedFromWindow(holder)
		val position = holder.adapterPosition
		if (isHeaderPosition(position) || isFooterPosition(position)) {
			return
		}
		val itemPosition = getRealItemPosition(position)
		val dataItem = getItem(itemPosition) ?: return
		dataItem.onViewDetachedFromWindow(holder)
	}
}