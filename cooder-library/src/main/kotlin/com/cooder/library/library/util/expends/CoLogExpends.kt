@file:JvmName("CoLogExpends")

package com.cooder.library.library.log.expends

import com.cooder.library.library.log.CoLog
import com.cooder.library.library.log.CoLogLevel
import com.cooder.library.library.log.CoLogLevel.ASSERT
import com.cooder.library.library.log.CoLogLevel.DEBUG
import com.cooder.library.library.log.CoLogLevel.ERROR
import com.cooder.library.library.log.CoLogLevel.INFO
import com.cooder.library.library.log.CoLogLevel.VERBOSE
import com.cooder.library.library.log.CoLogLevel.WARN

/**
 * 日志打印
 */
@JvmOverloads
fun <T> T.log(level: CoLogLevel = INFO) {
	when (level) {
		VERBOSE -> CoLog.v(this)
		DEBUG -> CoLog.d(this)
		INFO -> CoLog.i(this)
		WARN -> CoLog.w(this)
		ERROR -> CoLog.e(this)
		ASSERT -> CoLog.a(this)
	}
}