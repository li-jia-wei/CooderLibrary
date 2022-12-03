package com.cooder.cooder.library.app.coroutine

import com.cooder.cooder.library.log.CooderLog
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/10 19:26
 *
 * 介绍：CountDownLatchDemo
 */
class CountDownLatchDemo {
	
	fun start() {
		val countDownLatch = CountDownLatch(5)
		val executor = Executors.newFixedThreadPool(countDownLatch.count.toInt())
		repeat(countDownLatch.count.toInt()) {
			executor.execute {
				Thread.sleep(4000)
				CooderLog.i("${Thread.currentThread().name}准备好了")
				countDownLatch.countDown()
			}
		}
		countDownLatch.await()
		CooderLog.i("全部执行完成")
	}
}