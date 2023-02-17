@file:JvmName("Displays")

package com.cooder.cooder.library.util.expends

import android.content.res.Resources
import android.util.TypedValue

/**
 * 单位：px，将转化为px
 */
val Number.px: Float
	get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, this.toFloat(), Resources.getSystem().displayMetrics)

/**
 * 单位：dp，将转化为px
 */
val Number.dp: Float
	get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics)

/**
 * 单位：sp，将转化为px
 */
val Number.sp: Float
	get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics)

/**
 * 单位：px，将转化为px
 */
val Number.pxInt: Int
	get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, this.toFloat(), Resources.getSystem().displayMetrics).toInt()

/**
 * 单位：dp，将转化为px
 */
val Number.dpInt: Int
	get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()

/**
 * 单位：sp，将转化为px
 */
val Number.spInt: Int
	get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()