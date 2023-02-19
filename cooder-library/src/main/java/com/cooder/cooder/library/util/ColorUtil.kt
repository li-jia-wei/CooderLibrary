package com.cooder.cooder.library.util

import androidx.annotation.ColorInt

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/2/19 23:39
 *
 * 介绍：颜色转换工具
 */
object ColorUtil {
	
	/**
	 * 得到红色
	 */
	fun toRed(@ColorInt color: Int): Int {
		return (color and 0xFF0000) shr 16
	}
	
	/**
	 * 得到绿色
	 */
	fun toGreen(@ColorInt color: Int): Int {
		return (color and 0x00FF00) shr 8
	}
	
	/**
	 * 得到蓝色
	 */
	fun toBlue(@ColorInt color: Int): Int {
		return (color and 0x0000FF)
	}
}