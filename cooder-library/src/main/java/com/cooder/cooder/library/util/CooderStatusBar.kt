package com.cooder.cooder.library.util

import android.app.Activity
import android.graphics.Color
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.core.view.WindowCompat

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
	 * 设置状态栏沉浸式布局
	 */
	@JvmOverloads
	@JvmStatic
	fun setStatusBarImmersive(activity: Activity, darkContent: Boolean = true) {
		val window = activity.window
		window.statusBarColor = Color.TRANSPARENT
		val controller = WindowCompat.getInsetsController(window, window.decorView)
		controller.isAppearanceLightStatusBars = darkContent
		WindowCompat.setDecorFitsSystemWindows(window, false)
	}
}