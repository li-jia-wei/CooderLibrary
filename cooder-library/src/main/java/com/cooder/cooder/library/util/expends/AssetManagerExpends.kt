@file:JvmName("Expends")
@file:JvmMultifileClass

package com.cooder.cooder.library.util.expends

import android.content.res.AssetManager
import java.io.InputStreamReader

/**
 * 获取assets下文件内容
 */
fun AssetManager.readText(filename: String): String {
	val inputStream = this.open(filename)
	val reader = InputStreamReader(inputStream)
	val text = reader.readText()
	reader.close()
	inputStream.close()
	return text
}