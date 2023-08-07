package com.cooder.library.library.log.config

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/8/6 22:12
 *
 * 介绍：禁用日志配置
 */
class CoDisableLogConfig : CoLogConfig() {
	
	override val enable: Boolean = false
}