package com.cooder.cooder.library.executor

import android.os.Handler
import android.os.Looper
import androidx.annotation.IntRange
import com.cooder.cooder.library.log.CoLog
import kotlinx.coroutines.Runnable
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.ReentrantLock

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/12 00:00
 *
 * 介绍：CooderExecutor
 *
 * 支持按任务的优先级去执行、线程池暂停与恢复、异步结果主动回调主线程
 */
@Suppress("UNCHECKED_CAST")
object CoExecutor {
	
	private const val TAG = "CooderExecutor"
	
	private val cooderExecutor: ThreadPoolExecutor
	
	private val lock = ReentrantLock()
	private val pauseCondition = lock.newCondition()
	
	private var isPaused = false
	
	private val mainHandler = Handler(Looper.getMainLooper()!!)
	
	private val pauseCallbacks = mutableMapOf<String, () -> Unit>()
	private val resumeCallbacks = mutableMapOf<String, () -> Unit>()
	
	init {
		val cpuCount = Runtime.getRuntime().availableProcessors()
		val corePoolSize = cpuCount + 1
		val maxPoolSize = cpuCount * 2 + 1
		val blockingQueue: PriorityBlockingQueue<out Runnable> = PriorityBlockingQueue()
		val keepAliveTime = 30L
		val unit = TimeUnit.SECONDS
		val seq = AtomicLong()
		val threadFactory = ThreadFactory {
			val thread = Thread(it)
			thread.name = "cooder-executor-${seq.getAndIncrement()}"
			return@ThreadFactory thread
		}
		cooderExecutor = object : ThreadPoolExecutor(
			corePoolSize, maxPoolSize, keepAliveTime, unit, blockingQueue as BlockingQueue<Runnable>, threadFactory
		) {
			override fun beforeExecute(t: Thread?, r: Runnable?) {
				if (isPaused) {
					lock.lock()
					try {
						pauseCondition.await()
					} finally {
						lock.unlock()
					}
				}
			}
			
			override fun afterExecute(r: Runnable?, t: Throwable?) {
				CoLog.wt(TAG, "已执行完的任务的优先级是：${(r as PriorityRunnable).priority}")
			}
		}
	}
	
	/**
	 * 异步任务执行
	 */
	@JvmStatic
	@JvmOverloads
	fun execute(@IntRange(from = 0, to = 10) priority: Int = 0, runnable: Runnable) {
		this.cooderExecutor.execute(PriorityRunnable(priority, runnable))
	}
	
	/**
	 * 异步任务执行，主动回调主线程
	 */
	@JvmStatic
	@JvmOverloads
	fun <T> execute(@IntRange(from = 0, to = 10) priority: Int = 0, callable: Callable<T>) {
		this.cooderExecutor.execute(PriorityRunnable(priority, callable))
	}
	
	abstract class Callable<T> : Runnable {
		
		override fun run() {
			mainHandler.post {
				onPrepare()
			}
			val t = onBackground()
			mainHandler.post {
				onCompleted(t)
			}
		}
		
		/**
		 * 任务执行前
		 */
		abstract fun onPrepare()
		
		/**
		 * 任务执行中
		 */
		abstract fun onBackground(): T
		
		/**
		 * 任务执行完
		 */
		abstract fun onCompleted(t: T)
	}
	
	/**
	 * 优先级Runnable
	 */
	class PriorityRunnable(val priority: Int, private val runnable: Runnable) : Runnable, Comparable<PriorityRunnable> {
		
		override fun run() {
			this.runnable.run()
		}
		
		override fun compareTo(other: PriorityRunnable): Int {
			return if (this.priority < other.priority) 1 else if (this.priority > other.priority) -1 else 0
		}
	}
	
	/**
	 * 是否暂停
	 */
	@JvmStatic
	fun isPaused(): Boolean {
		return isPaused
	}
	
	/**
	 * 添加暂停线程时候执行的回调
	 */
	@JvmStatic
	fun putPauseCallback(name: String, callback: () -> Unit) {
		this.pauseCallbacks[name] = callback
	}
	
	/**
	 * 添加恢复线程时执行的回调
	 */
	@JvmStatic
	fun putResumeCallback(name: String, callback: () -> Unit) {
		this.resumeCallbacks[name] = callback
	}
	
	/**
	 * 移除暂停线程时候执行的回调
	 */
	@JvmStatic
	fun removePauseCallback(name: String) {
		this.pauseCallbacks.remove(name)
	}
	
	/**
	 * 移除恢复线程时执行的回调
	 */
	@JvmStatic
	fun removeResumeCallback(name: String) {
		this.resumeCallbacks.remove(name)
	}
	
	/**
	 * 暂停线程
	 */
	@JvmStatic
	@Synchronized
	fun pause() {
		if (!isPaused) {
			this.pauseCallbacks.values.forEach {
				it.invoke()
			}
			this.isPaused = true
		}
		CoLog.wt(TAG, "CooderExecutor is paused.")
	}
	
	/**
	 * 恢复线程
	 */
	@JvmStatic
	@Synchronized
	fun resume() {
		if (isPaused) {
			this.resumeCallbacks.values.forEach {
				it.invoke()
			}
			this.isPaused = false
			lock.lock()
			try {
				pauseCondition.signalAll()
			} finally {
				lock.unlock()
			}
		}
		CoLog.wt(TAG, "CooderExecutor is resume.")
	}
}