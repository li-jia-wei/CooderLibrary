@file:JvmName("ThrowExpends")

package com.cooder.library.library.util.expends

/**
 * 检查是否被重写
 */
inline fun <reified T : Any> T.isOverrideMethod(methodName: String, vararg paramTypes: Class<*>): Boolean {
	return try {
		this::class.java.getDeclaredMethod(methodName, *paramTypes)
	} catch (_: Exception) {
		null
	} != null
}