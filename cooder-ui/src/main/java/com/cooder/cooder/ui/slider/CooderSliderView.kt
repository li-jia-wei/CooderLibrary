package com.cooder.cooder.ui.slider

import android.content.Context
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
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cooder.cooder.library.util.dpInt
import com.cooder.cooder.ui.R
import com.cooder.cooder.ui.item.CooderViewHolder

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/1/15 15:03
 *
 * 介绍：CooderSliderView2
 */
class CooderSliderView @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private val menuItem = parseMenuItem(attributeSet)

    private val menuView = RecyclerView(context)
    private val contentView = RecyclerView(context)

    private var onMenuItemClickListener: ((holder: CooderViewHolder, position: Int) -> Unit)? = null
    private var onMenuItemClickRepeatedListener: ((holder: CooderViewHolder, position: Int) -> Unit)? = null
    private var ignoreMenuItemRepeatClick = true

    private var onContentItemClickListener: ((holder: CooderViewHolder, position: Int) -> Unit)? = null

    private companion object {
        private val MENU_ITEM_WIDTH = 100.dpInt
        private val MENU_ITEM_HEIGHT = 45.dpInt
        private val MENU_ITEM_TEXT_SIZE = 14.dpInt

        private val MENU_ITEM_TEXT_COLOR_NORMAL = R.color.cooder_slider_menu_text_color_normal
        private val MENU_ITEM_TEXT_COLOR_SELECT = R.color.cooder_slider_menu_text_color_select

        private val MENU_ITEM_BG_COLOR_NORMAL = R.color.cooder_slider_menu_bg_color_normal
        private val MENU_ITEM_BG_COLOR_SELECT = R.color.cooder_slider_menu_bg_color_select

        private val MENU_ITEM_RES_ID = R.layout.cooder_slider_menu_item
        private val CONTENT_ITEM_RES_ID = R.layout.cooder_slider_content_item
    }

    init {
        orientation = HORIZONTAL

        menuView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        menuView.overScrollMode = View.OVER_SCROLL_NEVER
        menuView.itemAnimator = null
        addView(menuView)

        contentView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        contentView.overScrollMode = View.OVER_SCROLL_NEVER
        contentView.itemAnimator = null
        addView(contentView)
    }

    /**
     * 绑定视图
     */
    fun bindMenuView(
        itemCount: Int,
        @LayoutRes resId: Int = MENU_ITEM_RES_ID,
        onBindView: (holder: CooderViewHolder, position: Int) -> Unit
    ) {
        menuView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        menuView.adapter = MenuAdapter(itemCount, resId, onBindView)
    }

    /**
     * 设置点击菜单项的监听事件
     * @param ignoreRepeatClick 当没有设置重复点击事件的时候生效，如果已经设置重复点击事件，则此选项无效
     */
    fun setOnMenuItemClickListener(ignoreRepeatClick: Boolean = true, listener: (holder: CooderViewHolder, position: Int) -> Unit) {
        this.ignoreMenuItemRepeatClick = ignoreRepeatClick
        this.onMenuItemClickListener = listener
    }

    /**
     * 设置点击已经选中的菜单项的监听事件
     */
    fun setOnMenuItemClickRepeatedListener(listener: (holder: CooderViewHolder, position: Int) -> Unit) {
        this.onMenuItemClickRepeatedListener = listener
    }

    /**
     * 绑定内容视图
     */
    fun bindContentView(
        itemCount: Int,
        @LayoutRes resId: Int = CONTENT_ITEM_RES_ID,
        layoutManager: LayoutManager = GridLayoutManager(context, 3),
        itemDecoration: RecyclerView.ItemDecoration? = null,
        onBindView: (holder: CooderViewHolder, position: Int) -> Unit,
        onItemClickListener: (holder: CooderViewHolder, position: Int) -> Unit
    ) {
        if (contentView.layoutManager == null) {
            contentView.layoutManager = layoutManager
            if (itemDecoration != null) {
                contentView.addItemDecoration(itemDecoration)
            }
        }
        val contentAdapter = contentView.adapter as ContentAdapter
        contentAdapter.update(itemCount, onBindView, onItemClickListener)
        contentView.scrollToPosition(0)
    }

    /**
     * 菜单适配器
     */
    private inner class MenuAdapter(
        private val itemCount: Int,
        private val menuItemLayoutResId: Int,
        private val onBindView: (holder: CooderViewHolder, position: Int) -> Unit
    ) : RecyclerView.Adapter<CooderViewHolder>() {

        private var currentSelectedPosition = 0

        private var lastSelectedPosition = -1

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CooderViewHolder {
            val itemView = LayoutInflater.from(context).inflate(menuItemLayoutResId, parent, false)
            itemView.layoutParams = RecyclerView.LayoutParams(menuItem.width, menuItem.height)
            if (menuItem.normalBackground != null) {
                itemView.background = menuItem.normalBackground
            } else {
                itemView.setBackgroundColor(ContextCompat.getColor(context, MENU_ITEM_BG_COLOR_NORMAL))
            }
            itemView.findViewById<TextView>(R.id.menu_item_title)?.apply {
                this.setTextColor(menuItem.normalTextColor)
                this.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuItem.normalTextSize.toFloat())
            }

            menuItem.indicator?.let {
                itemView.findViewById<ImageView>(R.id.menu_item_indicator)?.setImageDrawable(it)
            }

            return CooderViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return itemCount
        }

        override fun onBindViewHolder(holder: CooderViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                this.currentSelectedPosition = holder.adapterPosition
                notifyItemChanged(currentSelectedPosition)
                notifyItemChanged(lastSelectedPosition)
            }

            setMenuItem(holder, holder.adapterPosition)

            if (currentSelectedPosition == holder.adapterPosition) {
                // 当点击了相同项且设置了重复点击事件才会执行
                if (lastSelectedPosition == currentSelectedPosition && onMenuItemClickRepeatedListener != null) {
                    onMenuItemClickRepeatedListener!!.invoke(holder, holder.adapterPosition)
                } else {
                    // 当点击了不同项或者不忽略重复点击事件时才执行
                    if (lastSelectedPosition != currentSelectedPosition || !ignoreMenuItemRepeatClick) {
                        onMenuItemClickListener?.invoke(holder, holder.adapterPosition)
                    }
                }
                this.lastSelectedPosition = currentSelectedPosition
            }
            this.onBindView.invoke(holder, holder.adapterPosition)
        }

        /**
         * 设置菜单项
         */
        private fun setMenuItem(holder: CooderViewHolder, currentPosition: Int) {
            val selected = currentSelectedPosition == currentPosition
            holder.findViewById<TextView>(R.id.menu_item_title)?.apply {
                this.setTextSize(TypedValue.COMPLEX_UNIT_PX, (if (selected) menuItem.selectTextSize else menuItem.normalTextSize).toFloat())
                this.setTextColor(if (selected) menuItem.selectTextColor else menuItem.normalTextColor)
            }
            holder.findViewById<ImageView>(R.id.menu_item_indicator)?.visibility = if (selected) View.VISIBLE else View.GONE
            if (selected) {
                if (menuItem.selectBackground != null) {
                    holder.itemView.background = menuItem.selectBackground
                } else {
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, MENU_ITEM_BG_COLOR_SELECT))
                }
            } else {
                if (menuItem.normalBackground != null) {
                    holder.itemView.background = menuItem.normalBackground
                } else {
                    holder.itemView.setBackgroundColor(ContextCompat.getColor(context, MENU_ITEM_BG_COLOR_NORMAL))
                }
            }
        }
    }

    /**
     * 内容适配器
     */
    private inner class ContentAdapter(
        @LayoutRes private val layoutResId: Int
    ) : RecyclerView.Adapter<CooderViewHolder>() {

        private var itemCount = 0
        private lateinit var onBindView: (holder: CooderViewHolder, position: Int) -> Unit
        private lateinit var onItemClickListener: (holder: CooderViewHolder, position: Int) -> Unit

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CooderViewHolder {
            val itemView = LayoutInflater.from(context).inflate(layoutResId, parent, false)
            val contentWidth = width - paddingStart - paddingRight - menuItem.width
            val spanCount = when (val layoutManager = (parent as RecyclerView).layoutManager) {
                is GridLayoutManager -> layoutManager.spanCount
                is StaggeredGridLayoutManager -> layoutManager.spanCount
                else -> 0
            }
            if (spanCount > 0) {
                val itemWidth = contentWidth / spanCount
                itemView.layoutParams = RecyclerView.LayoutParams(itemWidth, itemWidth)
            }
            return CooderViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return itemCount
        }

        override fun onBindViewHolder(holder: CooderViewHolder, position: Int) {
            this.onBindView.invoke(holder, holder.adapterPosition)
            holder.itemView.setOnClickListener {
                onContentItemClickListener?.invoke(holder, holder.adapterPosition)
            }
        }

        /**
         * 更新视图
         */
        fun update(
            itemCount: Int,
            onBindView: (holder: CooderViewHolder, position: Int) -> Unit,
            onItemClickListener: (holder: CooderViewHolder, position: Int) -> Unit
        ) {
            this.itemCount = itemCount
            this.onBindView = onBindView
            this.onItemClickListener = onItemClickListener
            notifyItemRangeChanged(0, itemCount)
        }
    }

    /**
     * 菜单项参数
     */
    private data class MenuItemMo(
        val width: Int,
        val height: Int,
        val normalTextSize: Int,
        val selectTextSize: Int,
        val normalTextColor: Int,
        val selectTextColor: Int,
        val indicator: Drawable?,
        val normalBackground: Drawable?,
        val selectBackground: Drawable?
    )

    /**
     * 解析菜单项参数
     */
    private fun parseMenuItem(attributeSet: AttributeSet?): MenuItemMo {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CooderSliderView)

        val width = typedArray.getDimensionPixelOffset(R.styleable.CooderSliderView_menuItemWidth, MENU_ITEM_WIDTH)
        val height = typedArray.getDimensionPixelOffset(R.styleable.CooderSliderView_menuItemHeight, MENU_ITEM_HEIGHT)
        val normalTextSize = typedArray.getDimensionPixelSize(R.styleable.CooderSliderView_menuItemTextSize, MENU_ITEM_TEXT_SIZE)
        val selectTextSize = typedArray.getDimensionPixelSize(R.styleable.CooderSliderView_menuItemSelectTextSize, MENU_ITEM_TEXT_SIZE)
        val normalTextColor = typedArray.getColor(R.styleable.CooderSliderView_menuItemTextColor, ContextCompat.getColor(context, MENU_ITEM_TEXT_COLOR_NORMAL))
        val selectTextColor = typedArray.getColor(R.styleable.CooderSliderView_menuItemSelectTextColor, ContextCompat.getColor(context, MENU_ITEM_TEXT_COLOR_SELECT))
        val indicator = typedArray.getDrawable(R.styleable.CooderSliderView_menuItemIndicator) ?: ContextCompat.getDrawable(context, R.drawable.shape_slider_indicator)
        val normalBackground = typedArray.getDrawable(R.styleable.CooderSliderView_menuItemBackground)
        val selectBackground = typedArray.getDrawable(R.styleable.CooderSliderView_menuItemSelectBackground)
        typedArray.recycle()

        return MenuItemMo(width, height, normalTextSize, selectTextSize, normalTextColor, selectTextColor, indicator, normalBackground, selectBackground)
    }
}