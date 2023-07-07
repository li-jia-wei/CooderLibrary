package com.cooder.library.library.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
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
@Entity(tableName = "tb_cache", indices = [Index(value = ["type", "key"], unique = true)])
class Cache {
	
	@PrimaryKey(autoGenerate = true)
	@ColumnInfo(name = "id")
	var id: Int = 0
	
	@ColumnInfo(name = "type")
	var type: String = ""
	
	@ColumnInfo(name = "key")
	var key: String = ""
	
	@ColumnInfo(name = "data")
	var data: ByteArray? = null
}