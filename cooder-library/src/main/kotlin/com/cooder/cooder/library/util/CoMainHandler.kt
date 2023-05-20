package com.cooder.cooder.library.util

import android.os.Handler
import android.os.Looper
import android.os.Message

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/7 21:12
 *
 * 介绍：主线程Handler
 */
object CoMainHandler {
	
	private val handler = Handler(Looper.getMainLooper())
	
	fun post(runnable: Runnable) {
		handler.post(runnable)
	}
	
	fun postDelay(delayMillis: Long, runnable: Runnable) {
		handler.postDelayed(runnable, delayMillis)
	}
	
	fun sendMessageAtFrontOfQueue(runnable: Runnable) {
		val msg = Message.obtain(handler, runnable)
		handler.sendMessageAtFrontOfQueue(msg)
	}
}