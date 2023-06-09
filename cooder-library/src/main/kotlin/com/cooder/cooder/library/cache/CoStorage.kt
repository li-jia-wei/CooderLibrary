package com.cooder.cooder.library.cache

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/7 13:24
 *
 * 介绍：基于Room的离线缓存框架
 */
object CoStorage {
	
	private val cacheDao: CacheDao by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { CacheDatabase.get().cacheDao }
	
	fun <T> saveCache(key: String, body: T) {
		val cache = Cache()
		cache.key = key
		cache.data = toByteArray(body)
		this.cacheDao.saveCache(cache)
	}
	
	fun <T> getCache(key: String): T? {
		val cache = this.cacheDao.getCache(key)
		return if (cache?.data != null) {
			toObject(cache.data!!)
		} else null
	}
	
	fun deleteCache(key: String) {
		val cache = Cache()
		cache.key = key
		this.cacheDao.deleteCache(cache)
	}
	
	private fun <T> toByteArray(body: T): ByteArray {
		var baos: ByteArrayOutputStream? = null
		var oos: ObjectOutputStream? = null
		try {
			baos = ByteArrayOutputStream()
			oos = ObjectOutputStream(baos)
			oos.writeObject(body)
			oos.flush()
			return baos.toByteArray()
		} catch (e: Exception) {
			e.printStackTrace()
		} finally {
			baos?.close()
			oos?.close()
		}
		return ByteArray(0)
	}
	
	private fun <T> toObject(byteArray: ByteArray): T? {
		var bais: ByteArrayInputStream? = null
		var ois: ObjectInputStream? = null
		try {
			bais = ByteArrayInputStream(byteArray)
			ois = ObjectInputStream(bais)
			@Suppress("UNCHECKED_CAST")
			return ois.readObject() as T
		} catch (e: Exception) {
			e.printStackTrace()
		} finally {
			bais?.close()
			ois?.close()
		}
		return null
	}
}