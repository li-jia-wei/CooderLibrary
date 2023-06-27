package com.cooder.library.app.coroutine

import com.cooder.library.library.log.CoLog
import java.util.concurrent.Executors
import java.util.concurrent.Semaphore

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/10 19:42
 *
 * 介绍：SemaphoreDemo
 */
class SemaphoreDemo {
	
	fun start() {
		val executor = Executors.newCachedThreadPool()
		// 限流
		val semaphore = Semaphore(3, true)
		repeat(10) {
			executor.execute {
				semaphore.acquire()
				CoLog.i("$it 开始游玩")
				Thread.sleep((0L..5000L).random())
				CoLog.i("$it 游玩好了")
				semaphore.release()
			}
		}
	}
}