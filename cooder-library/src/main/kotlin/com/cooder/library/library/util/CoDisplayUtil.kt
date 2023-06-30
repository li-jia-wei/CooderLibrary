package com.cooder.library.library.util

import android.annotation.SuppressLint

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
		DP,
		PX
	}
	
	@JvmStatic
	@JvmOverloads
	fun getDisplayWidth(unit: Unit = Unit.DP): Int {
		val context = AppGlobals.getBaseContext()
		val width = context.resources.displayMetrics.widthPixels
		return when (unit) {
			Unit.DP -> px2dp(width)
			Unit.PX -> width
		}
	}
	
	@JvmStatic
	@JvmOverloads
	fun getDisplayHeight(unit: Unit = Unit.DP): Int {
		val context = AppGlobals.getBaseContext()
		val height = context.resources.displayMetrics.heightPixels
		return when (unit) {
			Unit.DP -> px2dp(height)
			Unit.PX -> height
		}
	}
	
	/**
	 * 获取状态栏高度，单位：px
	 */
	@SuppressLint("DiscouragedApi", "InternalInsetResource")
	@JvmStatic
	@JvmOverloads
	fun getStatusBarsHeight(unit: Unit = Unit.DP): Int {
		val resources = AppGlobals.getBaseResources()
		val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
		val height = if (resourceId > 0) resources.getDimensionPixelOffset(resourceId) else 0
		return when (unit) {
			Unit.DP -> px2dp(height)
			Unit.PX -> height
		}
	}
	
	/**
	 * 获取底部导航栏宽度：单位：px
	 */
	@SuppressLint("DiscouragedApi", "InternalInsetResource")
	@JvmStatic
	@JvmOverloads
	fun getNavigationBarsHeight(unit: Unit = Unit.DP): Int {
		val resources = AppGlobals.getBaseResources()
		val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
		val height = if (resourceId > 0) resources.getDimensionPixelOffset(resourceId) else 0
		return when (unit) {
			Unit.DP -> px2dp(height)
			Unit.PX -> height
		}
	}
}