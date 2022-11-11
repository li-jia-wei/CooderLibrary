package com.cooder.cooder.library.restful.annotation

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/11 14:03
 *
 * 介绍：相对路径，如http://api.cooder.com/sample/first 之后的/sample/first
 *
 * @sample Sample
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class GET(val url: String)