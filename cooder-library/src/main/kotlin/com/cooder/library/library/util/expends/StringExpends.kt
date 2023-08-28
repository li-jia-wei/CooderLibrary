package com.cooder.library.library.util.expends

import java.util.Locale

/**
 * 开头大写
 */
fun String.capitalized(): String {
	return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() };
}