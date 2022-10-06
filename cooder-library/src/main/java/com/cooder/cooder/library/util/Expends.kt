package com.cooder.cooder.library.util

import android.content.res.Resources
import android.util.TypedValue

/**
 * dp2px
 */
val Number.px: Int
	get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, toFloat(), Resources.getSystem().displayMetrics).toInt()

/**
 * px2dp
 */
val Number.dp: Int
	get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, toFloat(), Resources.getSystem().displayMetrics).toInt()

/**
 * px2sp
 */
val Number.sp: Int
	get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, toFloat(), Resources.getSystem().displayMetrics).toInt()