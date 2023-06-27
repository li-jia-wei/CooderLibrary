@file:JvmName("CoLogExpends")

package com.cooder.library.library.log.expends

import com.cooder.library.library.log.CoLog
import com.cooder.library.library.log.CoLogType

/**
 * 日志打印
 */
@JvmOverloads
fun <T> T.log(@CoLogType.Type type: Int = CoLogType.I) {
	when (type) {
		CoLogType.A -> CoLog.a(this)
		CoLogType.D -> CoLog.d(this)
		CoLogType.E -> CoLog.e(this)
		CoLogType.I -> CoLog.i(this)
		CoLogType.V -> CoLog.v(this)
		CoLogType.W -> CoLog.w(this)
	}
}