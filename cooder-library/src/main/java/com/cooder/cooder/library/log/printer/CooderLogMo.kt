package com.cooder.cooder.library.log.printer

import com.cooder.cooder.library.log.CooderLogType
import java.text.SimpleDateFormat
import java.util.*

/**
 * 项目名称：CooderLibrary
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/9/26 10:40
 *
 * 文件介绍：CooderLogMo
 */
internal data class CooderLogMo(
	private val timeMillis: Long,
	@CooderLogType.Type val level: Int,
	private val tag: String,
	val log: String,
) {
	
	companion object {
		private val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
		private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)
	}
	
	fun getDate(): String {
		return dateFormat.format(timeMillis)
	}
	
	private fun getFlattened(): String {
		return "${format(timeMillis)} ${getLevel(level)}/$tag:"
	}
	
	private fun getLevel(@CooderLogType.Type level: Int): String {
		return when (level) {
			CooderLogType.V -> "V"
			CooderLogType.D -> "D"
			CooderLogType.I -> "I"
			CooderLogType.W -> "W"
			CooderLogType.E -> "E"
			CooderLogType.A -> "A"
			else -> "?"
		}
	}
	
	private fun format(timeMillis: Long): String {
		return sdf.format(timeMillis)
	}
	
	override fun toString(): String {
		return "${getFlattened()} $log"
	}
}