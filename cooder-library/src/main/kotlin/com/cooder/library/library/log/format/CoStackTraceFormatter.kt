package com.cooder.library.library.log.format

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/24 14:05
 *
 * 介绍：堆栈追踪的格式化器
 */
class CoStackTraceFormatter : CoLogFormatter<Array<StackTraceElement>> {
	
	override fun format(data: Array<StackTraceElement>): String {
		val sb = StringBuilder(128)
		if (data.isEmpty()) return ""
		if (data.size == 1) {
			return "\t- ${data.first()}"
		} else {
			data.forEachIndexed { index: Int, element: StackTraceElement ->
				if (index == 0) {
					sb.appendLine("\nstackTrace: ${data.size}")
				}
				sb.appendLine("${if (index != data.lastIndex) "├" else "└"} $element")
			}
		}
		return sb.toString()
	}
}