package com.cooder.cooder.library.log

import com.cooder.cooder.library.log.util.CooderStackTraceUtil

/**
 * 项目名称：CooderLibrary
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/9/23 23:28
 *
 * 文件介绍：日志打印
 */
object CooderLog {
	
	/**
	 * Verbose级别
	 */
	fun <T> v(vararg contents: T?) {
		log(CooderLogType.V, *contents)
	}
	
	/**
	 * Verbose级别，包含TAG
	 */
	fun <T> vt(tag: String, vararg contents: T?) {
		log(CooderLogType.V, tag, *contents)
	}
	
	/**
	 * Debug级别
	 */
	fun <T> d(vararg contents: T?) {
		log(CooderLogType.D, *contents)
	}
	
	/**
	 * Debug级别，包含TAG
	 */
	fun <T> dt(tag: String, vararg contents: T?) {
		log(CooderLogType.D, tag, *contents)
	}
	
	/**
	 * Info级别
	 */
	fun <T> i(vararg contents: T?) {
		log(CooderLogType.I, *contents)
	}
	
	/**
	 * Info级别，包含TAG
	 */
	fun <T> it(tag: String, vararg contents: T?) {
		log(CooderLogType.I, tag, *contents)
	}
	
	/**
	 * Warn级别
	 */
	fun <T> w(vararg contents: T?) {
		log(CooderLogType.W, *contents)
	}
	
	/**
	 * Warn级别，包含TAG
	 */
	fun <T> wt(tag: String, vararg contents: T?) {
		log(CooderLogType.W, tag, *contents)
	}
	
	/**
	 * Error级别
	 */
	fun <T> e(vararg contents: T?) {
		log(CooderLogType.E, *contents)
	}
	
	/**
	 * Error级别，包含TAG
	 */
	fun <T> et(tag: String, vararg contents: T?) {
		log(CooderLogType.E, tag, *contents)
	}
	
	/**
	 * Assert级别
	 */
	fun <T> a(vararg contents: T?) {
		log(CooderLogType.A, *contents)
	}
	
	/**
	 * Assert级别，包含TAG
	 */
	fun <T> at(tag: String, vararg contents: T?) {
		log(CooderLogType.A, tag, *contents)
	}
	
	private fun <T> log(@CooderLogType.Type type: Int, vararg contents: T?) {
		log(type, CooderLogManager.getInstance().config.globalTag(), *contents)
	}
	
	private fun <T> log(@CooderLogType.Type type: Int, tag: String, vararg contents: T?) {
		log(CooderLogManager.getInstance().config, type, tag, *contents)
	}
	
	/**
	 * 日志打印
	 * @param config 自定义日志配置
	 * @param type 日志级别
	 * @param tag TAG
	 * @param contents 打印的信息
	 */
	fun <T> log(config: CooderLogConfig, @CooderLogType.Type type: Int, tag: String, vararg contents: T?) {
		if (!config.enable()) return
		val sb = StringBuilder()
		if (config.includeThread()) {
			val thread = CooderLogConfig.COODER_THREAD_FORMATTER.format(Thread.currentThread())
			sb.append(thread)
		}
		if (config.includeStackTrack() && config.stackTrackDepth() > 0) {
			val realStackTrace = CooderStackTraceUtil.getCroppedRealStackTrack(Throwable().stackTrace, javaClass.`package`!!.name, config.stackTrackDepth())
			val stackTrace = CooderLogConfig.COODER_STACK_TRACE_FORMATTER.format(realStackTrace)
			sb.append(stackTrace)
		}
		val body = parseBody(contents, config)
		sb.append(body)
		val printers = if (config.printers() != null) config.printers()!!.toList() else CooderLogManager.getInstance().getPrinters()
		if (printers.isEmpty()) return
		printers.forEach {
			it.print(config, type, tag, sb.toString())
		}
	}
	
	/**
	 * 解析字符串
	 */
	private fun <T> parseBody(contents: Array<T>, config: CooderLogConfig): String {
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