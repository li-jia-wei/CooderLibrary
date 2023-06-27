package com.cooder.library.library.restful

import java.lang.reflect.Type

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/12 17:38
 *
 * 介绍：Restful 转换
 */
interface CoConvert {
	
	fun <T> convert(rawData: String, dataType: Type): CoResponse<T>
	
}