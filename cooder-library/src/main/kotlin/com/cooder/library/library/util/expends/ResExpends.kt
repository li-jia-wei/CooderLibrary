@file:JvmName("ResExpends")

package com.cooder.library.library.util.expends

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.cooder.library.library.util.CoRes

fun getString(@StringRes formatArgs: Int): String {
	return CoRes.getString(formatArgs)
}

fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String {
	return CoRes.getString(resId, *formatArgs)
}

fun getColor(@ColorRes resId: Int): Int {
	return CoRes.getColor(resId)
}

fun getColorStateList(@ColorRes resId: Int): ColorStateList? {
	return CoRes.getColorStateList(resId)
}

fun getDrawable(@DrawableRes resId: Int): Drawable? {
	return CoRes.getDrawable(resId)
}