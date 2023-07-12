package com.cooder.library.library.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.ConcurrentHashMap

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/5 09:36
 *
 * 介绍：LiveData消息总线
 */
object CoDataBus {
	
	private val eventMap = ConcurrentHashMap<String, StickyLiveData<*>>()
	
	fun <T> with(eventName: String): StickyLiveData<T> {
		var liveData = eventMap[eventName]
		if (liveData == null) {
			liveData = StickyLiveData<T>(eventName)
			eventMap[eventName] = liveData
		}
		@Suppress("UNCHECKED_CAST")
		return liveData as StickyLiveData<T>
	}
	
	class StickyLiveData<T>(
		private val eventName: String
	) : LiveData<T>() {
		
		var mStickyData: T? = null
			private set
		
		var mVersion = 0
			private set
		
		fun setStickyData(stickyData: T) {
			mStickyData = stickyData
			setValue(stickyData)
		}
		
		fun postStickData(stickyData: T) {
			mStickyData = stickyData
			postValue(stickyData)
		}
		
		override fun setValue(value: T) {
			this.mVersion++
			super.setValue(value)
		}
		
		override fun postValue(value: T) {
			this.mVersion++
			super.postValue(value)
		}
		
		/**
		 * 不启用粘性事件的观察者
		 */
		override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
			observe(owner, false, observer)
		}
		
		/**
		 * 启用粘性事件的观察者
		 */
		fun observeSticky(owner: LifecycleOwner, observer: Observer<in T>) {
			observe(owner, true, observer)
		}
		
		/**
		 * @param sticky 是否开启粘性消息
		 */
		private fun observe(owner: LifecycleOwner, sticky: Boolean, observer: Observer<in T>) {
			owner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
				// 监听事件发生销毁事件
				if (event == Lifecycle.Event.ON_DESTROY) {
					eventMap.remove(eventName)
				}
			})
			super.observe(owner, StickyObserver(this, sticky, observer))
		}
	}
	
	class StickyObserver<T>(
		private val stickyLiveData: StickyLiveData<T>,
		private val sticky: Boolean,
		private val observer: Observer<in T>
	) : Observer<T> {
		
		private var mLastVersion = stickyLiveData.mVersion
		
		override fun onChanged(value: T) {
			if (mLastVersion >= stickyLiveData.mVersion) {
				if (sticky && stickyLiveData.mStickyData != null) {
					observer.onChanged(stickyLiveData.mStickyData!!)
				}
				return
			}
			mLastVersion = stickyLiveData.mVersion
			observer.onChanged(value)
		}
	}
}