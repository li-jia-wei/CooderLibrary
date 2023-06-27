package com.cooder.library.library.util

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/6/25 12:54
 *
 * 介绍：CoRes
 */
object CoRes {
	
	private val context by lazy { AppGlobals.getBaseContext() }
	
	fun getString(@StringRes resId: Int): String {
		return context.getString(resId)
	}
	
	fun getString(@StringRes resId: Int, vararg args: Any?): String {
		return context.getString(resId, *args)
	}
	
	fun getColor(@ColorRes resId: Int): Int {
		return context.getColor(resId)
	}
	
	fun getColorStateList(@ColorRes resId: Int): ColorStateList? {
		return AppCompatResources.getColorStateList(context, resId)
	}
	
	fun getDrawable(@DrawableRes resId: Int): Drawable? {
		return AppCompatResources.getDrawable(context, resId)
	}
}