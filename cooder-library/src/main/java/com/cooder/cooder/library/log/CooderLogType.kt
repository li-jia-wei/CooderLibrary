package com.cooder.cooder.library.log

import android.util.Log
import androidx.annotation.IntDef

/**
 * 项目名称：CooderLibrary
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/9/23 23:32
 *
 * 文件介绍：日志类型
 */
object CooderLogType {
	
	@IntDef(V, D, I, W, E, A)
	@Retention(AnnotationRetention.SOURCE)
	annotation class Type
	
	const val V = Log.VERBOSE
	const val D = Log.DEBUG
	const val I = Log.INFO
	const val W = Log.WARN
	const val E = Log.ERROR
	const val A = Log.ASSERT
}