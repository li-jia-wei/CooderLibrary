package com.cooder.cooder.app.coroutine

import android.content.res.AssetManager
import com.cooder.cooder.library.executor.CooderExecutor
import com.cooder.cooder.library.log.CooderLog
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/10 02:15
 *
 * 介绍：CoroutineScene
 */
object CoroutineScene {
	
	/**
	 * 以此启动三个子线程，并且以同步的方式拿到他们的返回值，进而更新UI
	 */
	fun startScene1() {
		GlobalScope.launch(Dispatchers.Main) {
			val result1 = request1()
			val result2 = request2(result1)
			val result3 = request3(result2)
			updateUI1(result3)
		}
	}
	
	private suspend fun request1(): String {
		delay(2000)
		CooderLog.i("request1 work on ${Thread.currentThread().name}")
		return "result1"
	}
	
	private suspend fun request2(result1: String): String {
		delay(2000)
		CooderLog.i("request2 work on ${Thread.currentThread().name}")
		return "$result1|result2"
	}
	
	private suspend fun request3(result2: String): String {
		delay(2000)
		CooderLog.i("request3 work on ${Thread.currentThread().name}")
		return "$result2|result3"
	}
	
	private fun updateUI1(result3: String) {
		CooderLog.i("updateUI1 work on ${Thread.currentThread().name}")
		CooderLog.i(result3)
	}
	
	fun startScene2() {
		GlobalScope.launch(Dispatchers.Main) {
			val result4 = request4()
			val deferred5 = GlobalScope.async { request5(result4) }
			val deferred6 = GlobalScope.async { request5(result4) }
			updateUI2(deferred5.await(), deferred6.await())
			deferred5.cancel()
			deferred6.cancel()
		}
	}
	
	private suspend fun request4(): String {
		delay(2000)
		CooderLog.i("request4 work on ${Thread.currentThread().name}")
		return "result4"
	}
	
	private suspend fun request5(result4: String): String {
		delay(2000)
		CooderLog.i("request4 work on ${Thread.currentThread().name}")
		return "$result4|result5"
	}
	
	private suspend fun request6(result4: String): String {
		delay(2000)
		CooderLog.i("request4 work on ${Thread.currentThread().name}")
		return "$result4|result6"
	}
	
	private fun updateUI2(result5: String, result6: String) {
		CooderLog.i("updateUI2 work on ${Thread.currentThread().name}")
		CooderLog.i("$result5|$result6")
	}
	
	suspend fun parseAssetsFile(assetManager: AssetManager, filename: String): String {
		return suspendCancellableCoroutine {
			CooderExecutor.execute {
				val `is` = assetManager.open(filename)
				val isr = InputStreamReader(`is`)
				val br = BufferedReader(isr)
				var line: String?
				val sb = StringBuilder()
				do {
					line = br.readLine()
					if (line != null) sb.append(line) else break
				} while (true)
				br.close()
				isr.close()
				`is`.close()
				it.resumeWith(Result.success(sb.toString()))
			}
		}
	}
}