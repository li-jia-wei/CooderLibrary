package com.cooder.cooder.ui.slider

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cooder.cooder.library.util.expends.dpInt
import com.cooder.cooder.ui.R
import com.cooder.cooder.ui.item.CoViewHolder

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/15 08:11
 *
 * 介绍：SliderView
 */
class CoSliderView @JvmOverloads constructor(
	context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
	
	private var menuAttr = parseMenuAttr(attrs)
	
	private val menuView = RecyclerView(context)
	private val contentView = RecyclerView(context)
	
	private companion object {
		private val MENU_WIDTH = 100.dpInt
		private val MENU_HEIGHT = 45.dpInt
		private val TEXT_SIZE = 14.dpInt
		private val TEXT_COLOR_NORMAL = Color.parseColor("#666666")
		private val TEXT_COLOR_SELECT = Color.parseColor("#DD3127")
		private val BG_COLOR_NORMAL = Color.parseColor("#F7F8F9")
		private val BG_COLOR_SELECT = Color.parseColor("#FFFFFF")
		
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
		@LayoutRes private val layoutRes: Int, private val count: Int, private val callback: BindCallback
	) : RecyclerView.Adapter<CoViewHolder>() {
		
		private var currentSelectIndex = 0
		private var lastSelectIndex = 0
		
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
			title?.setTextSize(TypedValue.COMPLEX_UNIT_PX, (if (selected) menuAttr.selectTextSize else menuAttr.textSize).toFloat())
			indicator?.visibility = if (selected) View.VISIBLE else View.GONE
			holder.itemView.background = if (selected) menuAttr.selectBackground else menuAttr.background
			title?.isSelected = selected
		}
	}
	
	inner class ContentAdapter(@LayoutRes private val layoutRes: Int) : RecyclerView.Adapter<CoViewHolder>() {
		
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
	
	private fun parseMenuAttr(attrs: AttributeSet?): MenuAttr {
		val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CoSliderView)
		val width: Int = typedArray.getDimensionPixelOffset(R.styleable.CoSliderView_menuWidth, MENU_WIDTH)
		val height: Int = typedArray.getDimensionPixelOffset(R.styleable.CoSliderView_menuHeight, MENU_HEIGHT)
		val textSize: Int = typedArray.getDimensionPixelSize(R.styleable.CoSliderView_menuTextSize, TEXT_SIZE)
		val selectTextSize: Int = typedArray.getDimensionPixelSize(R.styleable.CoSliderView_menuSelectTextSize, TEXT_SIZE)
		val textColor: ColorStateList = typedArray.getColorStateList(R.styleable.CoSliderView_menuTextColor) ?: generateColorStateList()
		val indicator: Drawable? = typedArray.getDrawable(R.styleable.CoSliderView_menuIndicator) ?: ContextCompat.getDrawable(context, R.drawable.shape_slider_indicator)
		val background: Drawable = typedArray.getDrawable(R.styleable.CoSliderView_menuBackground) ?: ColorDrawable(BG_COLOR_NORMAL)
		val selectBackground: Drawable = typedArray.getDrawable(R.styleable.CoSliderView_menuSelectBackground) ?: ColorDrawable(BG_COLOR_SELECT)
		typedArray.recycle()
		return MenuAttr(width, height, textSize, selectTextSize, textColor, indicator, background, selectBackground)
	}
	
	private data class MenuAttr(
		var width: Int,
		var height: Int,
		var textSize: Int,
		var selectTextSize: Int,
		val textColor: ColorStateList,
		val indicator: Drawable?,
		val background: Drawable,
		val selectBackground: Drawable
	)
	
	private fun generateColorStateList(): ColorStateList {
		val states = arrayOf(intArrayOf(android.R.attr.state_selected), intArrayOf())
		val colors = intArrayOf(TEXT_COLOR_SELECT, TEXT_COLOR_NORMAL)
		return ColorStateList(states, colors)
	}
}