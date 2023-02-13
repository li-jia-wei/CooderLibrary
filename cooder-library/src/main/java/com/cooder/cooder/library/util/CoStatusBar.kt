@file:JvmName("CoStatusBar")

package com.cooder.cooder.library.util

import android.app.Activity
import android.graphics.Color
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat


/**
 * 设置状态栏
 * @param darkContent 深色内容
 * @param statusBarColor 状态栏颜色
 */
fun Activity.setStatusBar(darkContent: Boolean, @ColorInt statusBarColor: Int) {
	this.setStatusBarContent(darkContent)
	this.setStatusBarColor(statusBarColor)
}

/**
 * 设置状态栏
 * @param darkContent 深色内容
 * @param statusBarColorRes 状态栏颜色资源
 */
fun Activity.setStatusBarRes(darkContent: Boolean, @ColorRes statusBarColorRes: Int) {
	this.setStatusBarContent(darkContent)
	this.setStatusBarColorRes(statusBarColorRes)
}

/**
 * 设置状态栏内容颜色
 * @param darkContent 深色内容
 */
fun Activity.setStatusBarContent(darkContent: Boolean) {
	val controller = WindowCompat.getInsetsController(window, window.decorView)
	controller.isAppearanceLightStatusBars = darkContent
}

/**
 * 设置状态栏颜色
 * @param statusBarColor 状态栏颜色
 */
fun Activity.setStatusBarColor(@ColorInt statusBarColor: Int) {
	window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
	window.statusBarColor = statusBarColor
}

/**
 * 设置状态栏颜色
 * @param statusBarColorRes 状态栏颜色资源
 */
fun Activity.setStatusBarColorRes(@ColorRes statusBarColorRes: Int) {
	window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
	window.statusBarColor = ContextCompat.getColor(this, statusBarColorRes)
}

/**
 * 沉浸式状态栏，包含状态栏内容显示
 */
fun Activity.immersiveStatusBar(darkContent: Boolean) {
	window.statusBarColor = Color.TRANSPARENT
	val controller = WindowCompat.getInsetsController(window, window.decorView)
	controller.isAppearanceLightStatusBars = darkContent
	WindowCompat.setDecorFitsSystemWindows(window, true)
}

/**
 * 隐藏状态栏
 */
fun Activity.hintStatusBar() {
	val controller = WindowCompat.getInsetsController(window, window.decorView)
	controller.hide(WindowInsetsCompat.Type.statusBars())
	controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
}