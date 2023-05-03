package com.cooder.cooder.library.util

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette

//import androidx.

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/5/3 01:33
 *
 * 介绍：Bitmap的扩展工具
 */
object CoBitmapUtil {
	
	fun getBitmapBrightness(bitmap: Bitmap): Double {
		val palette = Palette.Builder(bitmap).generate()
		val dominantColor = palette.getDominantColor(Color.BLACK)
		return ColorUtils.calculateLuminance(dominantColor)
	}
}