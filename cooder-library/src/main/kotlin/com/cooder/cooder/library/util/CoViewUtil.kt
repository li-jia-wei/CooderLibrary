package com.cooder.cooder.library.util

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
 * 介绍：CoViewUtil
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
}