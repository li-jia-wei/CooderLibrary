package com.cooder.cooder.app.ui.slider

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/1/17 18:08
 *
 * 介绍：SliderMo
 */
data class SliderMo(
	val `data`: List<Data>
)

data class Data(
	val content: List<Content>,
	val menu: String
)

data class Content(
	val background: String,
	val name: String
)