package com.cooder.cooder.library.util

import android.content.res.AssetManager
import java.io.InputStreamReader

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/1/17 18:23
 *
 * 介绍：文件工具
 */
object CoFileUtil {
	
	/**
	 * 读取assets下的文件
	 * @param assets AssetManager
	 * @param filename 文件路径
	 */
	@JvmStatic
	fun readAssetFile(assets: AssetManager, filename: String): String {
		val inputStream = assets.open(filename)
		val reader = InputStreamReader(inputStream)
		val text = reader.readText()
		reader.close()
		inputStream.close()
		return text
	}
}