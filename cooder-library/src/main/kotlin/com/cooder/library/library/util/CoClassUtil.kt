package com.cooder.library.library.util

import android.content.pm.PackageManager
import android.os.Build
import androidx.core.os.BuildCompat.PrereleaseSdkCheck
import dalvik.system.DexFile

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/8/22 07:45
 *
 * 介绍：CoClassUtil
 */
object CoClassUtil {
	
	@PrereleaseSdkCheck
	fun <T> getClasses(packageName: String): List<Class<T>> {
		val classes = mutableListOf<Class<T>>()
		val context = AppGlobals.getBaseContext()
		val packageManager = context.packageManager
		val applicationInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			packageManager.getApplicationInfo(context.packageName, PackageManager.ApplicationInfoFlags.of(PackageManager.GET_META_DATA.toLong()))
		} else {
			packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
		}
		val sourceDir = applicationInfo.sourceDir
		val entries = DexFile(sourceDir).entries()
		while (entries.hasMoreElements()) {
			val className = entries.nextElement()
			if (className.startsWith(packageName)) {
				@Suppress("UNCHECKED_CAST")
				classes += Class.forName(className) as Class<T>
			}
		}
		return classes
	}
}