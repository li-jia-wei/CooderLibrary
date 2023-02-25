package com.cooder.cooder.library.log

import com.cooder.cooder.library.log.util.CoStackTraceUtil

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/23 23:28
 *
 * 介绍：日志打印
 */
object CoLog {
	
	/**
	 * Verbose级别
	 */
	@JvmStatic
	fun <T> v(vararg contents: T?) {
		log(CoLogType.V, *contents)
	}
	
	/**
	 * Verbose级别，包含TAG
	 */
	@JvmStatic
	fun <T> vt(tag: String, vararg contents: T?) {
		log(CoLogType.V, tag, *contents)
	}
	
	/**
	 * Debug级别
	 */
	@JvmStatic
	fun <T> d(vararg contents: T?) {
		log(CoLogType.D, *contents)
	}
	
	/**
	 * Debug级别，包含TAG
	 */
	@JvmStatic
	fun <T> dt(tag: String, vararg contents: T?) {
		log(CoLogType.D, tag, *contents)
	}
	
	/**
	 * Info级别
	 */
	@JvmStatic
	fun <T> i(vararg contents: T?) {
		log(CoLogType.I, *contents)
	}
	
	/**
	 * Info级别，包含TAG
	 */
	@JvmStatic
	fun <T> it(tag: String, vararg contents: T?) {
		log(CoLogType.I, tag, *contents)
	}
	
	/**
	 * Warn级别
	 */
	@JvmStatic
	fun <T> w(vararg contents: T?) {
		log(CoLogType.W, *contents)
	}
	
	/**
	 * Warn级别，包含TAG
	 */
	@JvmStatic
	fun <T> wt(tag: String, vararg contents: T?) {
		log(CoLogType.W, tag, *contents)
	}
	
	/**
	 * Error级别
	 */
	@JvmStatic
	fun <T> e(vararg contents: T?) {
		log(CoLogType.E, *contents)
	}
	
	/**
	 * Error级别，包含TAG
	 */
	@JvmStatic
	fun <T> et(tag: String, vararg contents: T?) {
		log(CoLogType.E, tag, *contents)
	}
	
	/**
	 * Assert级别
	 */
	@JvmStatic
	fun <T> a(vararg contents: T?) {
		log(CoLogType.A, *contents)
	}
	
	/**
	 * Assert级别，包含TAG
	 */
	@JvmStatic
	fun <T> at(tag: String, vararg contents: T?) {
		log(CoLogType.A, tag, *contents)
	}
	
	private fun <T> log(@CoLogType.Type type: Int, vararg contents: T?) {
		if (CoLogManager.isInit()) {
			log(type, CoLogManager.getInstance().config.globalTag(), *contents)
		}
	}
	
	private fun <T> log(@CoLogType.Type type: Int, tag: String, vararg contents: T?) {
		if (CoLogManager.isInit()) {
			log(CoLogManager.getInstance().config, type, tag, *contents)
		}
	}
	
	/**
	 * 日志打印
	 * @param config 自定义日志配置
	 * @param type 日志级别
	 * @param tag TAG
	 * @param contents 打印的信息
	 */
	fun <T> log(config: CoLogConfig, @CoLogType.Type type: Int, tag: String, vararg contents: T?) {
		if (!config.enable()) return
		val sb = StringBuilder()
		if (config.includeThread()) {
			val thread = CoLogConfig.CO_THREAD_FORMATTER.format(Thread.currentThread())
			sb.append(thread)
		}
		if (config.includeStackTrack() && config.stackTrackDepth() > 0) {
			val realStackTrace = CoStackTraceUtil.getCroppedRealStackTrack(Throwable().stackTrace, javaClass.`package`!!.name, config.stackTrackDepth())
			val stackTrace = CoLogConfig.CO_STACK_TRACE_FORMATTER.format(realStackTrace)
			sb.append(stackTrace)
		}
		val body = parseBody(contents, config)
		sb.append(body)
		val printers = if (config.printers() != null) config.printers()!!.toList() else CoLogManager.getInstance().getPrinters()
		if (printers.isEmpty()) return
		printers.forEach {
			it.print(config, type, tag, sb.toString())
		}
	}
	
	/**
	 * 解析字符串
	 */
	private fun <T> parseBody(contents: Array<T>, config: CoLogConfig): String {
		val jsonParser = config.injectJsonParser()
		if (jsonParser != null) {
			return jsonParser.toJson(contents)
		}
		val sb = StringBuilder()
		contents.forEach {
			sb.append(it).append(", ")
		}
		if (sb.isNotEmpty())
			sb.delete(sb.length - 2, sb.length)
		return sb.toString()
	}
}