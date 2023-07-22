@file:JvmName("ActivityExpends")

package com.cooder.library.library.util.expends

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

private val previousNavigationBarColors = mutableMapOf<Window, Int?>()

fun Activity.setStatusBar(darkContent: Boolean, @ColorInt statusBarColor: Int) {
	Impl26.setStatusBarContent(window, darkContent)
	Impl26.setStatusBarColor(window, statusBarColor)
}

fun Activity.setStatusBarContent(darkContent: Boolean) {
	Impl26.setStatusBarContent(window, darkContent)
}

fun Activity.setStatusBarColor(@ColorInt statusBarColor: Int) {
	Impl26.setStatusBarColor(window, statusBarColor)
}

fun Activity.setNavigationBarColor(@ColorInt navigationBarColor: Int) {
	Impl26.setNavigationBarColor(window, navigationBarColor)
}

fun Activity.immersiveStatusBar(darkContent: Boolean) {
	Impl26.immersiveStatusBar(window, darkContent)
}

fun Activity.hideStatusBar() {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
		Impl28.hideStatusBar(window)
	} else {
		Impl26.hideStatusBar(window)
	}
}

fun Activity.hideNavigationBar() {
	Impl26.hideNavigationBar(window)
}

fun Activity.showNavigationBar() {
	Impl26.showNavigationBar(window)
}

private object Impl26 {
	
	fun setStatusBarContent(window: Window, darkContent: Boolean) {
		val controller = WindowCompat.getInsetsController(window, window.decorView)
		controller.show(WindowInsetsCompat.Type.statusBars())
		controller.isAppearanceLightStatusBars = darkContent
		WindowCompat.setDecorFitsSystemWindows(window, true)
	}
	
	fun setStatusBarColor(window: Window, @ColorInt statusBarColor: Int) {
		val controller = WindowCompat.getInsetsController(window, window.decorView)
		controller.show(WindowInsetsCompat.Type.statusBars())
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
		window.statusBarColor = statusBarColor
		WindowCompat.setDecorFitsSystemWindows(window, true)
	}
	
	fun setNavigationBarColor(window: Window, @ColorInt color: Int) {
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
		window.navigationBarColor = color
	}
	
	fun immersiveStatusBar(window: Window, darkContent: Boolean) {
		previousNavigationBarColors[window] = window.navigationBarColor
		window.statusBarColor = Color.TRANSPARENT
		window.navigationBarColor = Color.TRANSPARENT
		val controller = WindowCompat.getInsetsController(window, window.decorView)
		controller.isAppearanceLightStatusBars = darkContent
		WindowCompat.setDecorFitsSystemWindows(window, false)
	}
	
	fun hideStatusBar(window: Window) {
		previousNavigationBarColors[window] = window.navigationBarColor
		window.navigationBarColor = Color.TRANSPARENT
		val controller = WindowCompat.getInsetsController(window, window.decorView)
		controller.hide(WindowInsetsCompat.Type.statusBars())
		controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
		WindowCompat.setDecorFitsSystemWindows(window, false)
	}
	
	fun hideNavigationBar(window: Window) {
		previousNavigationBarColors[window] = window.navigationBarColor
		window.navigationBarColor = Color.TRANSPARENT
		val controller = WindowCompat.getInsetsController(window, window.decorView)
		controller.hide(WindowInsetsCompat.Type.navigationBars())
		WindowCompat.setDecorFitsSystemWindows(window, false)
	}
	
	fun showNavigationBar(window: Window) {
		window.navigationBarColor = previousNavigationBarColors[window] ?: Color.BLACK
	}
}

@RequiresApi(Build.VERSION_CODES.P)
private object Impl28 {
	
	fun hideStatusBar(window: Window) {
		previousNavigationBarColors[window] = window.navigationBarColor
		window.navigationBarColor = Color.TRANSPARENT
		val controller = WindowCompat.getInsetsController(window, window.decorView)
		controller.hide(WindowInsetsCompat.Type.statusBars())
		controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
		WindowCompat.setDecorFitsSystemWindows(window, false)
		window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
	}
}