package com.cooder.library.library.util

import kotlin.math.pow
import kotlin.math.sqrt

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/5 23:45
 *
 * 介绍：距离计算工具
 */
object CoDistanceUtil {
	
	/**
	 * 两点间距离公式
	 */
	@JvmStatic
	fun between2Points(x1: Float, y1: Float, x2: Float, y2: Float): Float {
		return sqrt((x1 - x2).pow(2) + (y1 - y2).pow(2))
	}
}