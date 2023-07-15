package com.cooder.library.library.restful.annotation.method

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/15 21:15
 *
 * 介绍：PUT
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class PUT(val url: String, val bodyType: BodyType = BodyType.FORM_DATA)