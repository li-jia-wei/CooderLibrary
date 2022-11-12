package com.cooder.cooder.library.restful

import java.lang.reflect.Type

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/12 17:38
 *
 * 介绍：Cooder转换
 */
interface CooderConvert {
	
	fun <T> convert(rawData: String, dataType: Type): CooderResponse<T>
	
}