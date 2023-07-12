package com.cooder.library.ui.slider

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cooder.library.ui.R
import com.cooder.library.ui.item.CoViewHolder

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/15 08:11
 *
 * 介绍：侧边导航栏
 */
class CoSliderView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
	
	private var menuAttr = AttrParse.parseAttr(context, attrs)
	
	val menuView = RecyclerView(context)
	val contentView = RecyclerView(context)
	
	private companion object {
		private val MENU_LAYOUT_RES_ID = R.layout.co_slider_menu
		private val CONTENT_LAYOUT_RES_ID = R.layout.co_slider_content
	}
	
	init {
		orientation = HORIZONTAL
		menuView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
		menuView.overScrollMode = View.OVER_SCROLL_NEVER
		menuView.itemAnimator = null
		
		contentView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
		contentView.overScrollMode = View.OVER_SCROLL_NEVER
		contentView.itemAnimator = null
		
		addView(menuView)
		addView(contentView)
	}
	
	/**
	 * 绑定菜单视图
	 * @param layoutRes 布局文件
	 * @param itemCount 菜单数量
	 * @param callback 回调
	 */
	fun bindMenuView(@LayoutRes layoutRes: Int = MENU_LAYOUT_RES_ID, itemCount: Int, callback: BindCallback) {
		menuView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
		menuView.adapter = MenuAdapter(layoutRes, itemCount, callback)
	}
	
	fun bindContentView(
		@LayoutRes layoutRes: Int = CONTENT_LAYOUT_RES_ID,
		itemCount: Int,
		itemDecoration: RecyclerView.ItemDecoration?,
		layoutManager: RecyclerView.LayoutManager,
		callback: BindCallback
	) {
		if (contentView.layoutManager == null) {
			contentView.layoutManager = layoutManager
			contentView.adapter = ContentAdapter(layoutRes)
			itemDecoration?.let {
				contentView.addItemDecoration(it)
			}
		}
		val adapter = contentView.adapter as ContentAdapter
		adapter.update(itemCount, callback)
		adapter.notifyItemRangeChanged(0, adapter.itemCount)
		
		contentView.scrollToPosition(0)
	}
	
	interface BindCallback {
		
		fun onBindView(holder: CoViewHolder, position: Int)
		
		fun onItemClick(holder: CoViewHolder, position: Int)
	}
	
	inner class MenuAdapter(
		@LayoutRes private val layoutRes: Int,
		private val count: Int,
		private val callback: BindCallback
	) : RecyclerView.Adapter<CoViewHolder>() {
		
		private var currentSelectIndex = 0
		private var lastSelectIndex = 0
		private var bindViewCount = 0
		
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoViewHolder {
			val itemView = LayoutInflater.from(context).inflate(layoutRes, parent, false)
			itemView.layoutParams = RecyclerView.LayoutParams(menuAttr.width, menuAttr.height)
			itemView.background = menuAttr.background
			val title = itemView.findViewById<TextView>(R.id.menu_title)
			title.setTextColor(menuAttr.textColor)
			val indicator = itemView.findViewById<ImageView>(R.id.menu_indicator)
			indicator.setImageDrawable(menuAttr.indicator)
			return CoViewHolder(itemView)
		}
		
		override fun getItemCount(): Int {
			return this.count
		}
		
		override fun onBindViewHolder(holder: CoViewHolder, position: Int) {
			this.bindViewCount++
			holder.itemView.setOnClickListener {
				this.currentSelectIndex = holder.adapterPosition
				notifyItemChanged(currentSelectIndex)
				notifyItemChanged(lastSelectIndex)
			}
			if (currentSelectIndex == holder.adapterPosition) {
				callback.onItemClick(holder, holder.adapterPosition)
				lastSelectIndex = currentSelectIndex
			}
			applyItemAttr(holder, holder.adapterPosition)
			callback.onBindView(holder, holder.adapterPosition)
		}
		
		private fun applyItemAttr(holder: CoViewHolder, position: Int) {
			val selected = currentSelectIndex == position
			val title = holder.findViewById<TextView>(R.id.menu_title)
			val indicator = holder.findViewById<ImageView>(R.id.menu_indicator)
			if (isFirstViewBind()) {
				title.setTextSize(TypedValue.COMPLEX_UNIT_PX, (if (selected) menuAttr.selectTextSize else menuAttr.textSize).toFloat())
			}
			if (isFirstViewBind() || title.isSelected != selected) {
				title.setTextSizeScaling((if (selected) menuAttr.selectTextSize else menuAttr.textSize).toFloat())
				indicator.visibility = if (selected) View.VISIBLE else View.GONE
				holder.itemView.background = if (selected) menuAttr.selectBackground else menuAttr.background
				title.isSelected = selected
			}
		}
		
		private fun isFirstViewBind(): Boolean {
			return this.bindViewCount <= count
		}
		
		private fun TextView.setTextSizeScaling(toTextSize: Float) {
			com.cooder.library.library.anim.CoValueAnimator.start(this.textSize, toTextSize, 100L) {
				this.setTextSize(TypedValue.COMPLEX_UNIT_PX, it)
			}
		}
	}
	
	inner class ContentAdapter(
		@LayoutRes private val layoutRes: Int
	) : RecyclerView.Adapter<CoViewHolder>() {
		
		private var count = 0
		private lateinit var callback: BindCallback
		
		override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoViewHolder {
			val itemView = LayoutInflater.from(context).inflate(layoutRes, parent, false)
			val contentWidth = width - paddingStart - paddingEnd - menuAttr.width
			val spanCount = when (val layoutManager = (parent as RecyclerView).layoutManager) {
				is GridLayoutManager -> layoutManager.spanCount
				is StaggeredGridLayoutManager -> layoutManager.spanCount
				is LinearLayoutManager -> 1
				else -> 0
			}
			if (spanCount > 0) {
				val itemWidth = contentWidth / spanCount
				itemView.layoutParams = RecyclerView.LayoutParams(itemWidth, itemWidth)
			}
			return CoViewHolder(itemView)
		}
		
		override fun getItemCount(): Int {
			return count
		}
		
		override fun onBindViewHolder(holder: CoViewHolder, position: Int) {
			callback.onBindView(holder, holder.adapterPosition)
			holder.itemView.setOnClickListener {
				callback.onItemClick(holder, holder.adapterPosition)
			}
		}
		
		fun update(itemCount: Int, callback: BindCallback) {
			this.count = itemCount
			this.callback = callback
		}
	}
}