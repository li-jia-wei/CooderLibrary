@file:JvmName("SoftKeyboardExpends")

package com.cooder.library.library.util.expends

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment

private const val SHOW_SOFT_KEYBOARD_DELAY_MILLIS = 200L

/**
 * Activity 显示输入框
 *
 * @param editText 需要将焦点设置在哪一个输入框上
 */
fun Activity.showSoftKeyboard(editText: EditText) {
	window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
	currentFocus?.clearFocus()
	editText.requestFocus()
	editText.postDelayed({
		val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		manager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
	}, SHOW_SOFT_KEYBOARD_DELAY_MILLIS)
}

/**
 * Activity 隐藏输入框
 */
fun Activity.hideSoftKeyboard() {
	currentFocus?.clearFocus()
	val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	manager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

/**
 * Fragment 显示输入框
 *
 * @param editText 需要将焦点设置在哪一个输入框上
 */
fun Fragment.showSoftKeyboard(editText: EditText) {
	if (isRemoving || isDetached || activity == null) return
	val activity = requireActivity()
	activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
	activity.currentFocus?.clearFocus()
	editText.requestFocus()
	editText.postDelayed({
		val manager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		manager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
	}, SHOW_SOFT_KEYBOARD_DELAY_MILLIS)
}

/**
 * Fragment 隐藏输入框
 */
fun Fragment.hideSoftKeyboard() {
	if (isRemoving || isDetached || activity == null) return
	val currentFocus = requireActivity().currentFocus
	currentFocus?.clearFocus()
	val manager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	manager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

/**
 * View 显示输入框
 *
 * @param editText 需要将焦点设置在哪一个输入框上
 */
fun View.showSoftKeyboard(editText: EditText) {
	if (context !is Activity) return
	val currentFocus = (context as Activity).currentFocus
	currentFocus?.clearFocus()
	editText.requestFocus()
	editText.postDelayed({
		val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		manager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
	}, SHOW_SOFT_KEYBOARD_DELAY_MILLIS)
}

/**
 * View 隐藏输入框
 */
fun View.hideSoftKeyboard() {
	if (context !is Activity) return
	val focusView = (context as Activity).currentFocus
	focusView?.clearFocus()
	val manager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	manager.hideSoftInputFromWindow(focusView?.windowToken, 0)
}

