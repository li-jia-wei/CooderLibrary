package com.cooder.library.library.util

import android.graphics.Color

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/19 23:39
 *
 * 介绍：颜色转换工具
 */
object CoColorUtil {
	
	/**
	 * 计算中间值
	 * @param startColor 起始颜色
	 * @param endColor 结束颜色
	 * @param percentage 百分比
	 * @return 通过百分比计算出起始色和结束色中的颜色
	 */
	fun getCurrentColor(startColor: Int, endColor: Int, percentage: Float): Int {
		if (percentage == 0F) return startColor
		if (percentage == 1F) return endColor
		
		val startAlpha = Color.alpha(startColor)
		val startRed = Color.red(startColor)
		val startGreen = Color.green(startColor)
		val startBlue = Color.blue(startColor)
		
		val endAlpha = Color.alpha(endColor)
		val endRed = Color.red(endColor)
		val endGreen = Color.green(endColor)
		val endBlue = Color.blue(endColor)
		
		val diffAlpha = endAlpha - startAlpha
		val diffRed = endRed - startRed
		val diffGreen = endGreen - startGreen
		val diffBlue = endBlue - startBlue
		
		val currentAlpha = (startAlpha + diffAlpha * percentage).toInt()
		val currentRed = (startRed + diffRed * percentage).toInt()
		val currentGreen = (startGreen + diffGreen * percentage).toInt()
		val currentBlue = (startBlue + diffBlue * percentage).toInt()
		
		return Color.argb(currentAlpha, currentRed, currentGreen, currentBlue)
	}
}