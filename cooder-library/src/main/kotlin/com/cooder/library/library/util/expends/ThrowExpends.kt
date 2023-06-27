@file:JvmName("ThrowExpends")

package com.cooder.library.library.util.expends

/**
 * 检查是否被重写
 */
fun checkOverrideMethod(target: Class<*>, name: String, vararg parameterTypes: Class<*>) {
	val method = try {
		target.getDeclaredMethod(name, *parameterTypes)
	} catch (_: Exception) {
		null
	}
	if (method != null) {
		throw IllegalStateException("${target.simpleName} 重写了父类的 $name 方法")
	}
}