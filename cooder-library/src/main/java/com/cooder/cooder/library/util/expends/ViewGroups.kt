@file:JvmName("ViewGroups")

package com.cooder.cooder.library.util.expends

import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import java.util.*

/**
 * 查找指定的子View
 * @param T 要查找的子View的类型
 * @return 查找指定的子View
 */
inline fun <G : ViewGroup, reified T> G.findViewByType(): T? {
	val java = T::class.java
	val deque: Deque<View> = ArrayDeque()
	deque += this
	while (deque.isNotEmpty()) {
		val node = deque.removeFirst()
		if (node is T) {
			return java.cast(node)
		} else if (node is ViewGroup) {
			node.forEach {
				deque += it
			}
		}
	}
	return null
}