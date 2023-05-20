package com.cooder.cooder.library.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.ViewGroup
import androidx.core.view.iterator
import java.util.ArrayDeque
import java.util.Deque

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/29 13:03
 *
 * 介绍：视图工具
 */
object CoViewUtil {
	
	/**
	 * 查找指定的View
	 * @param group ViewGroup
	 * @param typeClass 如：RecyclerView::class.java
	 * @return 查找指定的子View
	 */
	@JvmStatic
	fun <T> findViewByType(group: ViewGroup?, typeClass: Class<T>): T? {
		if (group == null) return null
		val deque: Deque<View> = ArrayDeque()
		deque += group
		while (deque.isNotEmpty()) {
			val node: View = deque.removeFirst()
			if (typeClass.isInstance(node)) {
				return typeClass.cast(node)
			} else if (node is ViewGroup) {
				node.iterator().forEach {
					deque += it
				}
			}
		}
		return null
	}
	
	/**
	 * 判断Activity是否被销毁
	 */
	fun isActivityDestroy(context: Context): Boolean {
		val activity = findActivity(context) ?: return true
		return activity.isDestroyed && activity.isFinishing
	}
	
	/**
	 * 不要将context直接转换成Activity，还要转成ContextWrapper并获取baseContext进一步判断
	 */
	private fun findActivity(context: Context): Activity? {
		return when (context) {
			is Activity -> context
			is ContextWrapper -> findActivity(context.baseContext)
			else -> null
		}
	}
}