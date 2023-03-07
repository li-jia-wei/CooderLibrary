package com.cooder.cooder.library.cache

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cooder.cooder.library.util.AppGlobals

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/7 13:27
 *
 * 介绍：CacheDatabase
 */
@Database(entities = [Cache::class], version = 1)
abstract class CacheDatabase : RoomDatabase() {
	
	companion object {
		
		private var database: CacheDatabase
		
		init {
			val context = AppGlobals.getBaseContext()
			database = Room.databaseBuilder(context, CacheDatabase::class.java, "cooder_cache.db")
				.allowMainThreadQueries()
				.build()
		}
		
		fun get(): CacheDatabase {
			return database
		}
	}
	
	abstract val cacheDao: CacheDao
}