package com.cooder.library.library.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/7 13:33
 *
 * 介绍：缓存操作
 */
@Dao
interface CacheDao {
	
	@Insert(entity = Cache::class, onConflict = OnConflictStrategy.REPLACE)
	fun saveCache(cache: Cache): Long
	
	@Query(value = "select * from tb_cache where `cache_key` = :key")
	fun getCache(key: String): Cache?
	
	@Delete(entity = Cache::class)
	fun deleteCache(cache: Cache)
}