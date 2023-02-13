package com.cooder.cooder.library.util

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/26 12:14
 *
 * 介绍：屏幕单位转换工具
 */
object CoDisplayUtil {
	
	@JvmStatic
	fun dp2px(dp: Float): Float {
		val context = AppGlobals.getBaseContext()
		return dp * context.resources.displayMetrics.density
	}
	
	@JvmStatic
	fun dp2px(dp: Int): Int {
		val context = AppGlobals.getBaseContext()
		return (dp * context.resources.displayMetrics.density).toInt()
	}
	
	@JvmStatic
	fun px2dp(px: Float): Float {
		val context = AppGlobals.getBaseContext()
		return px / context.resources.displayMetrics.density
	}
	
	@JvmStatic
	fun px2dp(px: Int): Int {
		val context = AppGlobals.getBaseContext()
		return (px / context.resources.displayMetrics.density).toInt()
	}
	
	enum class Unit {
		DP, PX
	}
	
	@JvmStatic
	@JvmOverloads
	fun getDisplayWidth(unit: Unit = Unit.DP): Int {
		val context = AppGlobals.getBaseContext()
		return when (unit) {
			Unit.DP -> px2dp(context.resources.displayMetrics.widthPixels)
			Unit.PX -> context.resources.displayMetrics.widthPixels
		}
	}
	
	@JvmStatic
	@JvmOverloads
	fun getDisplayHeight(unit: Unit = Unit.DP): Int {
		val context = AppGlobals.getBaseContext()
		return when (unit) {
			Unit.DP -> px2dp(context.resources.displayMetrics.heightPixels)
			Unit.PX -> context.resources.displayMetrics.heightPixels
		}
	}
}