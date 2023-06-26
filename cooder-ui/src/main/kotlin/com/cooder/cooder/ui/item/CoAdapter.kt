package com.cooder.cooder.ui.item

import android.content.Context
import android.util.SparseArray
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.containsKey
import androidx.core.util.containsValue
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
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
 *z
 * 顺序：top(1) -> header(n) -> item(n) -> footer(n) -> bottom(1)
 */
class CoAdapter(
	context: Context
) : RecyclerView.Adapter<CoViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val dataItems = mutableListOf<CoDataItem<*, *>>()

    //	private val typeArray = SparseArray<CoDataItem<*, *>>()
    private val typePositions = SparseIntArray()
    private var recyclerViewRef: WeakReference<RecyclerView>? = null

    private val headers = SparseArray<View>()
    private val footers = SparseArray<View>()

    private var topView: View? = null
    private var bottomView: View? = null

    private companion object {
        private var BASE_HEADER_ITEM = 1000000
        private var BASE_FOOTER_ITEM = 2000000
        private var TOP_VIEW_ITEM = 3000000
        private var BOTTOM_VIEW_ITEM = 3000001
    }

    /**
     * 添加一个DataItem
     * @param dataItem DataItem
     * @param notify 是否刷新
     * @param index 在DataItem中插入的位置
	 */
	@JvmOverloads
	fun addItem(dataItem: CoDataItem<*, *>, notify: Boolean = true, index: Int = -1) {
		val isSetIndex = index >= 0 && index <= getItemSize()
		if (isSetIndex) {
			this.dataItems.add(index, dataItem)
		} else {
			this.dataItems += dataItem
		}
		if (notify) {
			if (isSetIndex) {
				notifyItemInserted(getToHeaderSize() + index)
			} else {
				notifyItemInserted(getToItemSize() - 1)
			}
		}
        dataItem.setAdapter(this)
	}
	
	/**
	 * 添加多个DataItem
	 * @param dataItems 多个DataItem
	 * @param notify 是否刷新
	 * @param startIndex 在DataItem中插入的位置
	 */
	@JvmOverloads
	fun addItems(dataItems: Collection<CoDataItem<*, *>>, notify: Boolean = true, startIndex: Int = -1) {
        val isSetStartIndex = startIndex >= 0 && startIndex <= getItemSize()
        if (isSetStartIndex) {
            this.dataItems.addAll(startIndex, dataItems)
        } else {
            this.dataItems += dataItems
        }
        if (notify) {
            if (isSetStartIndex) {
                notifyItemRangeInserted(getToHeaderSize() + startIndex, dataItems.size)
            } else {
                notifyItemRangeInserted(getToItemSize() - dataItems.size, dataItems.size)
            }
        }
        dataItems.forEach {
            it.setAdapter(this)
        }
    }
	
	/**
	 * 删除Item
	 * @param dataItem DataItem
	 */
	fun removeItem(dataItem: CoDataItem<*, *>?) {
        val type = dataItem!!.javaClass.hashCode()
        if (typePositions.containsKey(type)) {
            typePositions.delete(type)
        }
        val index = this.dataItems.indexOf(dataItem)
        if (index >= 0) {
            this.dataItems.removeAt(index)
            notifyItemRemoved(getToHeaderSize() + index)
        }
        dataItem.setAdapter(this)
    }
	
	/**
	 * 删除多个Item
	 * @param dataItems 多个DataItem
	 */
	fun removeItems(dataItems: Collection<CoDataItem<*, *>?>?) {
		dataItems?.forEach {
            val type = it!!.javaClass.hashCode()
            if (typePositions.containsKey(type)) {
                typePositions.delete(type)
            }
            val index = this.dataItems.indexOf(it)
            if (index >= 0) {
                this.dataItems.removeAt(index)
                notifyItemRemoved(getToHeaderSize() + index)
            }
        }
	}
	
	/**
	 * 通过索引删除DataItem
	 * @param index 在DataItem中的位置
	 * @return 获取被删除的DataItem
	 */
	fun removeItemAt(index: Int): CoDataItem<*, *>? {
		if (index >= 0 && index < getItemSize()) {
            val remove = dataItems.removeAt(index)
            val type = remove.javaClass.hashCode()
            if (typePositions.containsKey(type)) {
                typePositions.delete(type)
            }
            notifyItemRemoved(getToHeaderSize() + index)
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
	fun removeItemsAt(startIndex: Int, itemCount: Int): Collection<CoDataItem<*, *>> {
		val items = mutableListOf<CoDataItem<*, *>>()
		if (startIndex >= 0 && startIndex < getItemSize()) {
			val removeCount = min(getItemSize() - startIndex, itemCount)
			repeat(removeCount) {
                val item = dataItems.removeAt(startIndex)
                val type = item.javaClass.hashCode()
                if (typePositions.containsKey(type)) {
                    typePositions.delete(type)
                }
                items += item
            }
			notifyItemRangeRemoved(getToHeaderSize() + startIndex, removeCount)
		}
		return items
	}
	
	/**
	 * 删除所有DataItem
	 */
	fun removeAllItems() {
		notifyItemRangeRemoved(getToHeaderSize(), getItemSize())
		dataItems.forEach {
            val type = it.javaClass.hashCode()
            if (typePositions.containsKey(type)) {
                typePositions.delete(type)
            }
        }
		this.dataItems.clear()
	}
	
	/**
	 * 刷新DataItem
	 * @param dataItem DataItem
	 */
	fun refreshItem(dataItem: CoDataItem<*, *>) {
		val index = dataItems.indexOf(dataItem)
		if (index >= 0 && index < getItemSize()) {
			notifyItemChanged(getToHeaderSize() + index)
		}
	}
	
	/**
	 * 刷新多个Item
	 * @param dataItems 多个DataItem
	 */
	fun refreshItems(dataItems: Collection<CoDataItem<*, *>>) {
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
			notifyItemChanged(getToHeaderSize() + index)
		}
	}
	
	/**
	 * 刷新多个DataItemItem
	 * @param startIndex 在DataItem中的开始位置
	 * @param itemCount DataItem的数量
	 */
	fun refreshItemsAt(startIndex: Int, itemCount: Int) {
		val refreshCount = min(getItemSize() - startIndex, itemCount)
		notifyItemRangeChanged(getToHeaderSize() + startIndex, refreshCount)
	}
	
	/**
	 * 刷新所有的DataItem
	 */
	fun refreshAllItems() {
		notifyItemRangeChanged(getToHeaderSize(), getItemSize())
	}
	
	/**
	 * 添加HeaderView
	 * @param view HeaderView
	 */
	fun addHeaderView(view: View) {
		if (!headers.containsValue(view)) {
			headers.put(BASE_HEADER_ITEM++, view)
			notifyItemInserted(getToHeaderSize() - 1)
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
			notifyItemRemoved(getTopSize() + index)
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
		notifyItemRangeRemoved(getTopSize(), getHeaderSize())
		headers.clear()
	}
	
	/**
	 * 添加FooterView
	 * @param view FooterView
	 */
	fun addFooterView(view: View) {
		if (!footers.containsValue(view)) {
			footers.put(BASE_FOOTER_ITEM++, view)
			notifyItemInserted(getToFooterSize())
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
			notifyItemRemoved(getToItemSize() + index)
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
		notifyItemRangeRemoved(getToItemSize(), getFooterSize())
		footers.clear()
	}
	
	/**
	 * 添加顶部View
	 */
	fun setTopView(view: View) {
		if (this.topView == null) {
			this.topView = view
			notifyItemInserted(0)
		} else {
			this.topView = view
			notifyItemChanged(0)
		}
	}
	
	/**
	 * 移除顶部View
	 */
	fun removeTopView() {
		if (this.topView != null) {
			this.topView = null
			notifyItemRemoved(0)
		}
	}
	
	/**
	 * 添加底部View
	 */
	fun setBottomView(view: View) {
		if (this.bottomView == null) {
			this.bottomView = view
			notifyItemInserted(itemCount - 1)
		} else {
			this.bottomView = view
			notifyItemChanged(itemCount - 1)
		}
	}
	
	/**
	 * 移除底部View
	 */
	fun removeBottomView() {
		if (this.bottomView != null) {
			this.bottomView = null
			notifyItemRemoved(itemCount)
		}
	}
	
	/**
	 * 清楚所有HeaderView、DataItem和FooterView
	 */
	fun removeAll() {
		removeTopView()
		removeAllHeaderViews()
		removeAllItems()
		removeAllFooterView()
		removeBottomView()
	}
	
	/**
	 * 获取ItemView类型
	 */
	override fun getItemViewType(position: Int): Int {
		if (isTopViewPosition(position)) {
			return TOP_VIEW_ITEM
		}
		if (isBottomViewPosition(position)) {
			return BOTTOM_VIEW_ITEM
		}
		if (isHeaderPosition(position)) {
			val headerPosition = getRealHeaderPosition(position)
			return headers.keyAt(headerPosition)
		}
		if (isFooterPosition(position)) {
			val footerPosition = getRealFooterPosition(position)
			return footers.keyAt(footerPosition)
		}
		val itemPosition = getRealItemPosition(position)
		val dataItem = dataItems[itemPosition]
		val type = dataItem.javaClass.hashCode()
        typePositions.put(type, itemPosition)
		return type
	}
	
	/**
	 * 判断是否是Header
	 */
	private fun isHeaderPosition(position: Int): Boolean {
		return position in (getTopSize() until getToHeaderSize())
	}
	
	/**
	 * 判断是否是Footer
	 */
	private fun isFooterPosition(position: Int): Boolean {
		return position in (getToItemSize() until getToFooterSize())
	}
	
	/**
	 * 判断是否是TopView
	 */
	private fun isTopViewPosition(position: Int): Boolean {
		return position in (0 until getTopSize())
	}
	
	/**
	 * 判断是否是BottomView
	 */
	private fun isBottomViewPosition(position: Int): Boolean {
		return position in (getToFooterSize() until itemCount)
	}
	
	/**
	 * 判断是否不是Item的索引
	 */
	private fun isNotItemPosition(position: Int): Boolean {
		return position !in (getToHeaderSize() until getToItemSize())
	}
	
	/**
	 * 创建ViewHolder
	 */
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoViewHolder {
        if (viewType == TOP_VIEW_ITEM) {
            return CoViewHolder(topView!!)
        }
        if (viewType == BOTTOM_VIEW_ITEM) {
            return CoViewHolder(bottomView!!)
        }
        if (headers.containsKey(viewType)) {
            val view = headers[viewType]
            return CoViewHolder(view)
        }
        if (footers.containsKey(viewType)) {
            val view = footers[viewType]
            return CoViewHolder(view)
        }
        val position = typePositions[viewType]
        val dataItem = dataItems[position]
        val viewHolder = dataItem.onCreateViewHolder(LayoutInflater.from(parent.context), parent)
        if (viewHolder != null) return viewHolder
        var view = dataItem.getItemView(LayoutInflater.from(parent.context), parent)
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
	private fun createViewHolderInternal(javaClass: Class<CoDataItem<*, *>>, view: View): CoViewHolder {
		val superclass = javaClass.genericSuperclass
		if (superclass is ParameterizedType) {
			val arguments = superclass.actualTypeArguments
			for (argument in arguments) {
				if (argument is Class<*> && CoViewHolder::class.java.isAssignableFrom(argument)) {
					try {
						return argument.getConstructor(View::class.java).newInstance(view) as CoViewHolder
					} catch (e: Exception) {
						e.printStackTrace()
					}
				}
			}
		}
		return CoViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: CoViewHolder, position: Int) {
		if (isNotItemPosition(position)) {
			return
		}
		val itemPosition = getRealItemPosition(position)
		val dataItem = getItem(itemPosition)
		dataItem?.onBindData(holder, position)
	}
	
	private fun getItem(position: Int): CoDataItem<*, CoViewHolder>? {
		if (position < 0 || position >= getItemSize()) {
			return null
		}
		@Suppress("UNCHECKED_CAST")
		return dataItems[position] as CoDataItem<*, CoViewHolder>
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
						if (isNotItemPosition(position)) {
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
	override fun onViewAttachedToWindow(holder: CoViewHolder) {
		super.onViewAttachedToWindow(holder)
		val recyclerView = getAttachRecyclerView()
		if (recyclerView != null) {
			val position = recyclerView.getChildAdapterPosition(holder.itemView)
			val itemPosition = getRealItemPosition(position)
			val dataItem = getItem(itemPosition) ?: return
			// 适配StaggeredGridLayout
			val lp = holder.itemView.layoutParams
			if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
				val manager = recyclerView.layoutManager as StaggeredGridLayoutManager
				if (isNotItemPosition(position)) {
					lp.isFullSpan = true
					return
				}
				val spanSize = dataItem.getSpanSize()
				if (spanSize == manager.spanCount) {
					lp.isFullSpan = true
				}
			}
			dataItem.onViewAttachedToWindow(holder)
		}
	}
	
	/**
	 * 当此适配器创建的视图已与其窗口分离时调用
	 */
	override fun onViewDetachedFromWindow(holder: CoViewHolder) {
		super.onViewDetachedFromWindow(holder)
		val position = holder.adapterPosition
		if (isNotItemPosition(position)) {
			return
		}
		val itemPosition = getRealItemPosition(position)
		val dataItem = getItem(itemPosition) ?: return
		dataItem.onViewDetachedFromWindow(holder)
	}
	
	/**
	 * 获取Header的真实索引位置
	 */
	private fun getRealHeaderPosition(position: Int): Int {
		return position - getTopSize()
	}
	
	/**
	 * 获取DataItem的真实索引位置
	 */
	private fun getRealItemPosition(position: Int): Int {
		return position - getToHeaderSize()
	}
	
	/**
	 * 获取Footer的真实索引位置
	 */
	private fun getRealFooterPosition(position: Int): Int {
		return position - getToItemSize()
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
	 * 获取顶部视图数量
	 * @return 0 or 1
	 */
	fun getTopSize(): Int {
		return if (topView != null) 1 else 0
	}
	
	/**
	 * 获取底部视图数量
	 * @return 0 or 1
	 */
	fun getBottomSize(): Int {
		return if (bottomView != null) 1 else 0
	}
	
	/**
	 * 获取从topView到headerView的数量
	 */
	private fun getToHeaderSize(): Int {
		return getTopSize() + getHeaderSize()
	}
	
	/**
	 * 获取从topView到dataItem的数量
	 */
	private fun getToItemSize(): Int {
		return getToHeaderSize() + getItemSize()
	}
	
	/**
	 * 获取从topView到footerView的数量
	 */
	private fun getToFooterSize(): Int {
		return getToItemSize() + getFooterSize()
	}
	
	/**
	 * 获取Item总数
	 */
	override fun getItemCount(): Int {
		return getHeaderSize() + getItemSize() + getFooterSize() + getTopSize() + getBottomSize()
	}
	
	/**
	 * 判断ItemCount是否为0
	 */
	fun isEmpty(): Boolean {
		return itemCount == 0
	}
	
	/**
	 * 移除动画
	 */
	fun clearAnimation() {
		val recyclerView = recyclerViewRef?.get()
		val anim = recyclerView?.itemAnimator as SimpleItemAnimator?
		anim?.supportsChangeAnimations = false
		recyclerView?.itemAnimator = null
	}
}