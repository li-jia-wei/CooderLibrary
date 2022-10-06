package com.cooder.cooder.library.util

import android.view.View
import android.view.ViewGroup
import androidx.core.view.iterator
import java.util.*

/**
 * 项目名称：CooderLibrary
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/9/29 13:03
 *
 * 文件介绍：CooderViewUtil
 */
object CooderViewUtil {
	
	/**
	 * 查找指定的View
	 * @param group ViewGroup
	 * @param cls 如：RecyclerView::class.java
	 * @return 查找指定的View
	 */
	fun <T> findTypeView(group: ViewGroup?, cls: Class<T>): T? {
		group ?: return null
		val deque: Deque<View> = ArrayDeque()
		deque += group
		while (deque.isNotEmpty()) {
			val node: View = deque.removeFirst()
			if (cls.isInstance(node)) {
				return cls.cast(node)
			} else if (node is ViewGroup) {
				node.iterator().forEach {
					deque += it
				}
			}
		}
		return null
	}
}