package com.cooder.library.library.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.Resources

object AppGlobals {
	
	private var application: Application? = null
	private var lock = Any()
	
	@SuppressLint("PrivateApi")
	fun getApplication(): Application {
		try {
			if (application == null) {
				synchronized(lock) {
					if (application == null) {
						application = Class.forName("android.app.ActivityThread")
							.getMethod("currentApplication")
							.invoke(null) as Application
					}
				}
			}
		} catch (e: Exception) {
			e.printStackTrace()
		}
		return application ?: throw IllegalStateException("Failed to get application!")
	}
	
	/**
	 * 获取BaseContext
	 */
	fun getBaseContext(): Context {
		if (application != null)
			return application!!.baseContext
		return getApplication().baseContext ?: throw IllegalStateException("Failed to get application!")
	}
	
	/**
	 * 获取Resources
	 */
	fun getBaseResources(): Resources {
		return getBaseContext().resources
	}
}