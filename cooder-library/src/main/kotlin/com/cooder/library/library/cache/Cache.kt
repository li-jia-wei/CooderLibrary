package com.cooder.library.library.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/3/7 13:25
 *
 * 介绍：缓存表
 */
@Entity(tableName = "tb_cache")
class Cache {
	
	@PrimaryKey(autoGenerate = false)
	@ColumnInfo(name = "cache_key")
	var key: String = ""
	
	@ColumnInfo(name = "cache_data")
	var data: ByteArray? = null
}