@file:JvmName("Views")

package com.cooder.cooder.library.util.expends

import android.view.View
import android.view.ViewGroup

fun View.addView(child: View) {
	if (this is ViewGroup) {
		this.addView(child)
	} else {
		throw IllegalStateException("${this::class.java} is not type ViewGroup")
	}
}