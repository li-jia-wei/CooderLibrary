package com.cooder.library.library.log

import android.util.Log

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/23 23:32
 *
 * 介绍：日志类型
 */
enum class CoLogLevel(var level: Int) {
	VERBOSE(Log.VERBOSE),
	DEBUG(Log.DEBUG),
	INFO(Log.INFO),
	WARN(Log.WARN),
	ERROR(Log.ERROR),
	ASSERT(Log.ASSERT)
}