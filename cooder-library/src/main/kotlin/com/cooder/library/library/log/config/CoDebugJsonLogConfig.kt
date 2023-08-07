package com.cooder.library.library.log.config

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/8/6 22:47
 *
 * 介绍：CoDebugJsonLogConfig
 */
class CoDebugJsonLogConfig(
	private val jsonParser: JsonParser,
	override val globalTag: String
) : CoLogConfig() {
	
	override val enable: Boolean = true
	
	override fun injectJsonParser(): JsonParser = jsonParser
}