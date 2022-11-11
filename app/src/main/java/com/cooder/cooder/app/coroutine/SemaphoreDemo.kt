package com.cooder.cooder.app.coroutine

import com.cooder.cooder.library.log.CooderLog
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
				CooderLog.i("$it 开始游玩")
				Thread.sleep((0L..5000L).random())
				CooderLog.i("$it 游玩好了")
				semaphore.release()
			}
		}
	}
}