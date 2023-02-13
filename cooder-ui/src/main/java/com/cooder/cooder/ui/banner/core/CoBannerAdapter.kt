package com.cooder.cooder.ui.banner.core

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.viewpager.widget.PagerAdapter

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/20 20:14
 *
 * 介绍：CooderBannerAdapter
 */
class CoBannerAdapter(
	private val context: Context
) : PagerAdapter() {
	
	/**
	 * Bind适配器
	 */
	private var bindAdapter: IBindAdapter? = null
	
	/**
	 * 是否开启自动轮播
	 */
	private var autoPlay = true
	
	/**
	 * 是否可以循环切换
	 */
	private var loop = true
	
	/**
	 * Banner点击监听器
	 */
	private var onBannerClickListener: ICoBanner.OnBannerClickListener? = null
	
	/**
	 * ViewHolder缓存
	 */
	private var cacheViews = SparseArray<CoBannerViewHolder>()
	
	private var models: List<CoBannerMo>? = null
	
	@LayoutRes
	private var layoutResId = -1
	
	/**
	 * 设置BannerData
	 */
	fun setBannerData(models: List<CoBannerMo>) {
		this.models = models
		// 初始化数据
		initCachedView()
		notifyDataSetChanged()
	}
	
	/**
	 * 设置Bind适配器
	 */
	fun setBindAdapter(bindAdapter: IBindAdapter) {
		this.bindAdapter = bindAdapter
	}
	
	/**
	 * 设置是否自动播放
	 */
	fun setAutoPlay(autoPlay: Boolean) {
		this.autoPlay = autoPlay
	}
	
	/**
	 * 设置是否能循环切换
	 */
	fun setLoop(loop: Boolean) {
		this.loop = loop
	}
	
	/**
	 * 设置Banner点击事件
	 */
	fun setOnBannerClickListener(onBannerClickListener: ICoBanner.OnBannerClickListener) {
		this.onBannerClickListener = onBannerClickListener
	}
	
	/**
	 * 设置LayoutResId
	 */
	fun setLayoutResId(@LayoutRes layoutResId: Int) {
		this.layoutResId = layoutResId
	}
	
	override fun getCount(): Int {
		return if (autoPlay || loop) Int.MAX_VALUE else getRealCount()
	}
	
	/**
	 * 获取Banner页面真实数量
	 */
	fun getRealCount(): Int {
		return if (models == null) 0 else models!!.size
	}
	
	/**
	 * 获取初次展示的Item的位置
	 *
	 * @return 初次展示的Item的位置
	 */
	fun getFirstItem(): Int {
		return Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2) % getRealCount()
	}
	
	override fun isViewFromObject(view: View, obj: Any): Boolean {
		return view === obj
	}
	
	/**
	 * 实例化Item
	 */
	override fun instantiateItem(container: ViewGroup, position: Int): Any {
		var realPosition = position
		if (getRealCount() > 0) {
			realPosition = position % getRealCount()
		}
		val viewHolder = cacheViews[realPosition]
		if (container == viewHolder.rootView.parent) {
			container.removeView(viewHolder.rootView)
		}
		// 数据绑定
		onBind(viewHolder, models!![realPosition], realPosition)
		if (viewHolder.rootView.parent != null && viewHolder.rootView.parent is ViewGroup) {
			(viewHolder.rootView.parent as ViewGroup).removeView(viewHolder.rootView)
		}
		container.addView(viewHolder.rootView)
		return viewHolder.rootView
	}
	
	/**
	 * fix: 防止未展示的View被销毁，导致获取到缓存里的View出现白屏
	 */
	override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
		// 不要使用以下注释方法，防止缓存中的View被销毁导致Banner出现白屏
		// super.destroyItem(container, position, obj)
	}
	
	override fun getItemPosition(obj: Any): Int {
		// 让Item每次都会刷新
		return POSITION_NONE
	}
	
	private fun onBind(viewHolder: CoBannerViewHolder, mo: CoBannerMo, position: Int) {
		viewHolder.rootView.setOnClickListener {
			onBannerClickListener?.onBannerClick(viewHolder, mo, position)
		}
		bindAdapter?.onBind(viewHolder, mo, position)
	}
	
	/**
	 * 初始化缓存View
	 */
	private fun initCachedView() {
		cacheViews = SparseArray()
		models?.forEachIndexed { index, _ ->
			val viewHolder = CoBannerViewHolder(createView(LayoutInflater.from(context), null))
			cacheViews[index] = viewHolder
		}
	}
	
	/**
	 * 创建View
	 */
	private fun createView(layoutInflater: LayoutInflater, parent: ViewGroup?): View {
		if (layoutResId == -1) {
			throw IllegalStateException("You must first call the setLayoutResId method.")
		}
		val view = layoutInflater.inflate(layoutResId, parent, false)
		return view
	}
	
	/**
	 * BannerViewHolder
	 */
	class CoBannerViewHolder(val rootView: View) {
		private var viewSparseArray: SparseArray<View>? = null
		
		@Suppress("UNCHECKED_CAST")
		fun <V : View> findViewById(@IdRes id: Int): V {
			if (rootView !is ViewGroup) {
				return rootView as V
			}
			if (viewSparseArray == null) {
				viewSparseArray = SparseArray(1)
			}
			var childView: V? = viewSparseArray!![id] as V?
			if (childView == null) {
				childView = rootView.findViewById(id)
				viewSparseArray!![id] = childView
			}
			return childView!!
		}
	}
}