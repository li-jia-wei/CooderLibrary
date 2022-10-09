package com.cooder.cooder.ui.refresh

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/11 00:22
 *
 * 介绍：下拉刷新的Overlay视图，可以重载这个类来定义自己的Overlay
 */
abstract class CooderOverView @JvmOverloads constructor(
	context: Context,
	attributeSet: AttributeSet? = null,
	defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

}
