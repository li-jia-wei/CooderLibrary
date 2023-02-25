package com.cooder.cooder.library.restful.annotation

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/11 13:45
 *
 * 介绍：设置消息头
 *
 * @sample Sample
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Headers(vararg val value: String)