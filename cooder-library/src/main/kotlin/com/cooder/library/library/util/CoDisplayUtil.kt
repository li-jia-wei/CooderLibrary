package com.cooder.library.library.util

import android.app.Activity
import android.os.Build
import android.view.WindowInsets

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
	
	fun getStatusBarHeight(activity: Activity): Int {
		val insets = activity.window.decorView.rootWindowInsets
		return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
			@Suppress("DEPRECATION")
			insets?.systemWindowInsetTop ?: 0
		} else {
			insets?.getInsets(WindowInsets.Type.statusBars())?.top ?: 0
		}
	}
}