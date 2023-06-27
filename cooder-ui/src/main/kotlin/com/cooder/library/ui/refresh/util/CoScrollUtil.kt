package com.cooder.library.ui.refresh.util

import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ScrollView
import androidx.recyclerview.widget.RecyclerView
import com.cooder.library.library.util.CoViewUtil

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/11 17:33
 *
 * 介绍：滑动工具类
 */
object CoScrollUtil {
	
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
			val adapterView = CoViewUtil.findViewByType(child, AdapterView::class.java)
			if (adapterView != null) return adapterView
			val recyclerView = CoViewUtil.findViewByType(child, RecyclerView::class.java)
			if (recyclerView != null) return recyclerView
		}
		return child
	}
}