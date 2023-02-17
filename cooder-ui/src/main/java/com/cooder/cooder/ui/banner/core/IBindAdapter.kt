package com.cooder.cooder.ui.banner.core

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/20 20:01
 *
 * 介绍：CoBanner的数据绑定接口，基于该接口可以实现数据的绑定和框架层解耦
 */
interface IBindAdapter {
	
	fun onBind(viewHolder: CoBannerAdapter.CoBannerViewHolder, mo: CoBannerMo, position: Int)
}