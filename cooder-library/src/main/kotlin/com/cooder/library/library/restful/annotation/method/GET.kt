package com.cooder.library.library.restful.annotation.method

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/11 14:03
 *
 * 介绍：相对路径
 *
 * 绝对路径：http://www.example.com/example
 *
 * /test => http://www.example.com/example/test
 *
 * //test => http://www.example.com/test
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class GET(val url: String)