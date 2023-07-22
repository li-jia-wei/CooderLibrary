@file:Suppress("DEPRECATION", "UNCHECKED_CAST")

package com.cooder.library.library.util.expends

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import java.io.Serializable

inline fun <reified T : Parcelable> Intent.getCompatParcelableExtra(name: String): T? {
	return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
		this.getParcelableExtra(name, T::class.java)
	} else {
		this.getParcelableExtra(name)
	}
}

inline fun <reified T : Serializable> Intent.getCompatSerializableExtra(name: String): T? {
	return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
		this.getSerializableExtra(name, T::class.java)
	} else {
		this.getSerializableExtra(name) as T
	}
}

inline fun <reified T : Parcelable> Intent.getCompatParcelableArrayListExtra(name: String): ArrayList<T>? {
	return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
		this.getParcelableArrayListExtra(name, T::class.java)
	} else {
		this.getParcelableArrayListExtra(name)
	}
}

inline fun <reified T : Parcelable> Intent.getCompatParcelableArray(name: String): Array<T>? {
	return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
		this.getParcelableArrayExtra(name, T::class.java)
	} else {
		this.getParcelableArrayExtra(name) as Array<T>
	}
}

inline fun <reified T : Parcelable> Bundle.getCompatParcelable(key: String): T? {
	return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
		this.getParcelable(key, T::class.java)
	} else {
		this.getParcelable(key)
	}
}

inline fun <reified T : Serializable> Bundle.getCompatSerializable(key: String): T? {
	return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
		this.getSerializable(key, T::class.java)
	} else {
		this.getSerializable(key) as T
	}
}

inline fun <reified T : Parcelable> Bundle.getCompatSparseParcelableArray(key: String): SparseArray<T>? {
	return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
		this.getSparseParcelableArray(key, T::class.java)
	} else {
		this.getSparseParcelableArray(key)
	}
}

inline fun <reified T : Parcelable> Bundle.getCompatParcelableArray(key: String): Array<T>? {
	return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
		this.getParcelableArray(key, T::class.java)
	} else {
		this.getParcelableArray(key) as Array<T>
	}
}

inline fun <reified T : Parcelable> Bundle.getCompatParcelableArrayList(key: String): ArrayList<T>? {
	return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
		this.getParcelableArrayList(key, T::class.java)
	} else {
		this.getParcelableArrayList(key)
	}
}