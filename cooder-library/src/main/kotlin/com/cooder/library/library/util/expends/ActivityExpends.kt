@file:JvmName("ActivityExpends")

package com.cooder.library.library.util.expends

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

private val previousNavigationBarColors = mutableMapOf<Activity, Int?>()

fun Activity.setStatusBar(darkContent: Boolean, @ColorInt statusBarColor: Int) {
	Impl26.setStatusBarContent(this, darkContent)
	Impl26.setStatusBarColor(this, statusBarColor)
}

fun Activity.setStatusBarContent(darkContent: Boolean) {
	Impl26.setStatusBarContent(this, darkContent)
}

fun Activity.setStatusBarColor(@ColorInt statusBarColor: Int) {
	Impl26.setStatusBarColor(this, statusBarColor)
}

fun Activity.setNavigationBarColor(@ColorInt navigationBarColor: Int) {
	Impl26.setNavigationBarColor(this, navigationBarColor)
}

fun Activity.immersiveStatusBar(darkContent: Boolean) {
	Impl26.immersiveStatusBar(this, darkContent)
}

fun Activity.hintStatusBar() {
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
		Impl28.hintStatusBar(this)
	} else {
		Impl26.hintStatusBar(this)
	}
}

fun Activity.hintNavigationBar() {
	Impl26.hintNavigationBar(this)
}

fun Activity.showNavigationBar() {
	Impl26.showNavigationBar(this)
}

private object Impl26 {
	
	fun setStatusBarContent(activity: Activity, darkContent: Boolean) {
		val window = activity.window
		val controller = WindowCompat.getInsetsController(window, window.decorView)
		controller.show(WindowInsetsCompat.Type.statusBars())
		controller.isAppearanceLightStatusBars = darkContent
		WindowCompat.setDecorFitsSystemWindows(window, true)
	}
	
	fun setStatusBarColor(activity: Activity, @ColorInt statusBarColor: Int) {
		val window = activity.window
		val controller = WindowCompat.getInsetsController(window, window.decorView)
		controller.show(WindowInsetsCompat.Type.statusBars())
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
		window.statusBarColor = statusBarColor
		WindowCompat.setDecorFitsSystemWindows(window, true)
	}
	
	fun setNavigationBarColor(activity: Activity, @ColorInt color: Int) {
		val window = activity.window
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
		window.navigationBarColor = color
	}
	
	fun immersiveStatusBar(activity: Activity, darkContent: Boolean) {
		val window = activity.window
		previousNavigationBarColors[activity] = window.navigationBarColor
		window.statusBarColor = Color.TRANSPARENT
		window.navigationBarColor = Color.TRANSPARENT
		val controller = WindowCompat.getInsetsController(window, window.decorView)
		controller.isAppearanceLightStatusBars = darkContent
		WindowCompat.setDecorFitsSystemWindows(window, false)
	}
	
	fun hintStatusBar(activity: Activity) {
		val window = activity.window
		previousNavigationBarColors[activity] = window.navigationBarColor
		window.navigationBarColor = Color.TRANSPARENT
		val controller = WindowCompat.getInsetsController(window, window.decorView)
		controller.hide(WindowInsetsCompat.Type.statusBars())
		controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
		WindowCompat.setDecorFitsSystemWindows(window, false)
	}
	
	fun hintNavigationBar(activity: Activity) {
		val window = activity.window
		previousNavigationBarColors[activity] = window.navigationBarColor
		window.navigationBarColor = Color.TRANSPARENT
		val controller = WindowCompat.getInsetsController(window, window.decorView)
		controller.hide(WindowInsetsCompat.Type.navigationBars())
	}
	
	fun showNavigationBar(activity: Activity) {
		val window = activity.window
		window.navigationBarColor = previousNavigationBarColors[activity] ?: Color.BLACK
	}
}

@RequiresApi(Build.VERSION_CODES.P)
private object Impl28 {
	
	fun hintStatusBar(activity: Activity) {
		val window = activity.window
		previousNavigationBarColors[activity] = window.navigationBarColor
		window.navigationBarColor = Color.TRANSPARENT
		val controller = WindowCompat.getInsetsController(window, window.decorView)
		controller.hide(WindowInsetsCompat.Type.statusBars())
		controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
		WindowCompat.setDecorFitsSystemWindows(window, false)
		window.attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
	}
}