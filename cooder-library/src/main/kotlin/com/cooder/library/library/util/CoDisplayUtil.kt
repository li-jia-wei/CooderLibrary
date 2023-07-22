package com.cooder.library.library.util

import android.annotation.SuppressLint
import android.util.TypedValue
import androidx.annotation.IntDef

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
	
	@IntDef(
		TypedValue.COMPLEX_UNIT_PX,
		TypedValue.COMPLEX_UNIT_DIP
	)
	annotation class UnitDef
	
	@JvmStatic
	@JvmOverloads
	fun getDisplayWidth(@UnitDef unit: Int = TypedValue.COMPLEX_UNIT_PX): Int {
		val context = AppGlobals.getBaseContext()
		val width = context.resources.displayMetrics.widthPixels
		return when (unit) {
			TypedValue.COMPLEX_UNIT_PX -> width
			TypedValue.COMPLEX_UNIT_DIP -> px2dp(width)
			else -> throw IllegalStateException("不支持的类型: $unit")
		}
	}
	
	@JvmStatic
	@JvmOverloads
	fun getDisplayHeight(@UnitDef unit: Int = TypedValue.COMPLEX_UNIT_PX): Int {
		val context = AppGlobals.getBaseContext()
		val height = context.resources.displayMetrics.heightPixels
		return when (unit) {
			TypedValue.COMPLEX_UNIT_PX -> height
			TypedValue.COMPLEX_UNIT_DIP -> px2dp(height)
			else -> throw IllegalStateException("不支持的类型: $unit")
		}
	}
	
	/**
	 * 获取状态栏高度，单位：px
	 */
	@SuppressLint("DiscouragedApi", "InternalInsetResource")
	@JvmStatic
	@JvmOverloads
	fun getStatusBarsHeight(@UnitDef unit: Int = TypedValue.COMPLEX_UNIT_PX): Int {
		val resources = AppGlobals.getBaseResources()
		val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
		val height = if (resourceId > 0) resources.getDimensionPixelOffset(resourceId) else 0
		return when (unit) {
			TypedValue.COMPLEX_UNIT_PX -> height
			TypedValue.COMPLEX_UNIT_DIP -> px2dp(height)
			else -> throw IllegalStateException("不支持的类型: $unit")
		}
	}
	
	/**
	 * 获取底部导航栏宽度：单位：px
	 */
	@SuppressLint("DiscouragedApi", "InternalInsetResource")
	@JvmStatic
	@JvmOverloads
	fun getNavigationBarsHeight(@UnitDef unit: Int = TypedValue.COMPLEX_UNIT_PX): Int {
		val resources = AppGlobals.getBaseResources()
		val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
		val height = if (resourceId > 0) resources.getDimensionPixelOffset(resourceId) else 0
		return when (unit) {
			TypedValue.COMPLEX_UNIT_PX -> height
			TypedValue.COMPLEX_UNIT_DIP -> px2dp(height)
			else -> throw IllegalStateException("不支持的类型: $unit")
		}
	}
}