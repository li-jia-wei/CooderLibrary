package com.cooder.library.library.util.expends

import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Activity的viewModel扩展
 */
inline fun <reified VM : ViewModel> ComponentActivity.viewModel(): Lazy<VM> {
	return lazy { ViewModelProvider(this)[VM::class.java] }
}

/**
 * Fragment的viewModel扩展
 */
inline fun <reified VM : ViewModel> Fragment.viewModel(): Lazy<VM> {
	return lazy { ViewModelProvider(this)[VM::class.java] }
}