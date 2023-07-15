package com.cooder.library.library.restful.annotation.method

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/11 14:53
 *
 * 介绍：POST提交
 *
 * @param formPost true:表单提交 false:json提交
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class POST(val url: String, val bodyType: BodyType = BodyType.FORM_DATA)