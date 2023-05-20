package com.cooder.cooder.library.log.printer

import com.cooder.cooder.library.log.CoLogType
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/26 10:40
 *
 * 介绍：日志模型
 */
internal data class CoLogMo(
	private val timeMillis: Long,
	@CoLogType.Type val level: Int,
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
	
	private fun getLevel(@CoLogType.Type level: Int): String {
		return when (level) {
			CoLogType.V -> "V"
			CoLogType.D -> "D"
			CoLogType.I -> "I"
			CoLogType.W -> "W"
			CoLogType.E -> "E"
			CoLogType.A -> "A"
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