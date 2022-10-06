package com.cooder.cooder.library.log.format

/**
 * 项目名称：CooderLibrary
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/9/24 14:05
 *
 * 文件介绍：堆栈追踪的格式化器
 */
class CooderStackTraceFormatter : CooderLogFormatter<Array<StackTraceElement>> {
	
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