package com.cooder.cooder.library.util

import android.app.Activity
import android.graphics.Color
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/20 13:02
 *
 * 介绍：CooderStatusBar
 */
object CooderStatusBar {
	
	/**
	 * 设置状态栏
	 */
	@JvmOverloads
	@JvmStatic
	fun setStatusBar(activity: Activity, darkContent: Boolean = true, statusBarColor: Int = Color.WHITE) {
		setStatusBarContent(activity, darkContent)
		setStatusBarColor(activity, statusBarColor)
	}
	
	/**
	 * 设置状态栏内容
	 * @param darkContent 内容颜色是否是黑色
	 */
	@JvmOverloads
	@JvmStatic
	fun setStatusBarContent(activity: Activity, darkContent: Boolean = true) {
		val window = activity.window
		val controller = WindowCompat.getInsetsController(window, window.decorView)
		controller.isAppearanceLightStatusBars = darkContent
	}
	
	/**
	 * 设置状态栏颜色
	 * @param statusBarColor 状态栏颜色
	 */
	@JvmOverloads
	@JvmStatic
	fun setStatusBarColor(activity: Activity, @ColorInt statusBarColor: Int = Color.WHITE) {
		val window = activity.window
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
		window.statusBarColor = statusBarColor
	}

	/**
	 * 沉浸式状态栏，包含状态栏内容显示
	 */
	@JvmOverloads
	@JvmStatic
	fun immersiveStatusBar(activity: Activity, darkContent: Boolean = true) {
		val window = activity.window
		window.statusBarColor = Color.TRANSPARENT
		val controller = WindowCompat.getInsetsController(window, window.decorView)
		controller.isAppearanceLightStatusBars = darkContent
		WindowCompat.setDecorFitsSystemWindows(window, true)
	}

	/**
	 * 隐藏状态栏
	 */
	fun hintStatusBar(activity: Activity) {
		val window = activity.window
		val controller = WindowCompat.getInsetsController(window, window.decorView)
		controller.hide(WindowInsetsCompat.Type.statusBars())
		controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
	}
}