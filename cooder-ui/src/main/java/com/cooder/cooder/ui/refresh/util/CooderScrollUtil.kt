package com.cooder.cooder.ui.refresh.util

import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import com.cooder.cooder.library.util.CooderViewUtil

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/11 17:33
 *
 * 介绍：滑动工具类
 */
object CooderScrollUtil {
	
	/**
	 * 判断child是否发生了滚动
	 * @return true 发生了滚动
	 */
	fun childScrolled(child: View): Boolean {
		if (child is AdapterView<*>) {
			val adapterView = child
			if (adapterView.firstVisiblePosition != 0 || adapterView.firstVisiblePosition == 0 && adapterView.getChildAt(0) != null && adapterView.getChildAt(0).top < 0) {
				return true
			}
		} else if (child.scrollY > 0) {
			return true
		}
		if (child is RecyclerView) {
			val recyclerView = child
			val view = recyclerView.getChildAt(0)
			val firstPosition = recyclerView.getChildAdapterPosition(view)
			return firstPosition != 0 || view.top != 0
		}
		return false
	}
	
	/**
	 * 查找可以滚动的子View
	 */
	fun findScrollableChild(viewGroup: ViewGroup): View {
		val child = viewGroup.getChildAt(1)
		if (child is RecyclerView || child is AbsListView || child is ScrollView) {
			return child
		}
		if (child is ViewGroup) {
			val adapterView = CooderViewUtil.findTypeView(child, AdapterView::class.java)
			if (adapterView != null) return adapterView
			val recyclerView = CooderViewUtil.findTypeView(child, RecyclerView::class.java)
			if (recyclerView != null) return recyclerView
		}
		return child
	}
}