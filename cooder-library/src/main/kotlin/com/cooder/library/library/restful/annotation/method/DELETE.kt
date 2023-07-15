package com.cooder.library.library.restful.annotation.method

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/15 21:18
 *
 * 介绍：DELETE
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DELETE(val url: String)