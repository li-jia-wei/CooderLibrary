package com.cooder.cooder.library.util

import android.content.Context

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/26 12:14
 *
 * 介绍：屏幕单位转换工具
 */
object CooderDisplayUtil {
	
	enum class Unit {
		DP, PX
	}
	
	@JvmStatic
	fun dp2px(context: Context, dp: Float): Float {
		return dp * context.resources.displayMetrics.density
	}
	
	@JvmStatic
	fun dp2px(context: Context, dp: Int): Int {
		return (dp * context.resources.displayMetrics.density).toInt()
	}
	
	@JvmStatic
	fun px2dp(context: Context, px: Float): Float {
		return px / context.resources.displayMetrics.density
	}
	
	@JvmStatic
	fun px2dp(context: Context, px: Int): Int {
		return (px / context.resources.displayMetrics.density).toInt()
	}
	
	@JvmStatic
	@JvmOverloads
	fun getDisplayWidth(context: Context, unit: Unit = Unit.DP): Int {
		return when (unit) {
			Unit.DP -> (context.resources.displayMetrics.widthPixels * context.resources.displayMetrics.density).toInt()
			Unit.PX -> context.resources.displayMetrics.widthPixels
		}
	}
	
	@JvmStatic
	@JvmOverloads
	fun getDisplayHeight(context: Context, unit: Unit = Unit.DP): Int {
		return when (unit) {
			Unit.DP -> (context.resources.displayMetrics.heightPixels * context.resources.displayMetrics.density).toInt()
			Unit.PX -> context.resources.displayMetrics.heightPixels
		}
	}
}