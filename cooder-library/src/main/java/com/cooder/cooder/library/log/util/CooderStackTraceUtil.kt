package com.cooder.cooder.library.log.util

import kotlin.math.min

/**
 * 项目名称：CooderLibrary
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/9/25 21:39
 *
 * 文件介绍：堆栈信息工具类
 */
internal object CooderStackTraceUtil {
	
	/**
	 * 获取要打印的堆栈信息
	 */
	fun getCroppedRealStackTrack(stackTrace: Array<StackTraceElement>, ignorePackage: String?, maxDepth: Int): Array<StackTraceElement> {
		val realStackTrace = getRealStackTrack(stackTrace, ignorePackage)
		return cropStackTrace(realStackTrace, maxDepth)
	}
	
	/**
	 * 获取除忽略包外的堆栈信息
	 */
	private fun getRealStackTrack(stackTrace: Array<StackTraceElement>, ignorePackage: String?): Array<StackTraceElement> {
		var ignoreDepth = 0
		if (ignorePackage != null) {
			for ((index, stack) in stackTrace.withIndex()) {
				if (!stack.className.startsWith(ignorePackage)) {
					ignoreDepth = index
					break
				}
			}
		}
		val realDepth = stackTrace.size - ignoreDepth
		return Array(realDepth) { stackTrace[it + ignoreDepth] }
	}
	
	/**
	 * 裁剪堆栈信息
	 */
	private fun cropStackTrace(stackTrace: Array<StackTraceElement>, maxDepth: Int): Array<StackTraceElement> {
		if (maxDepth == -1) return stackTrace
		if (maxDepth < -1) throw IllegalStateException("参数错误，请输入一个长度，-1：无限制长度，0：不显示长度")
		var realDepth = stackTrace.size
		if (maxDepth > 0) {
			realDepth = min(maxDepth, realDepth)
		}
		return Array(realDepth) { stackTrace[it] }
	}
}