package com.cooder.library.ui.tab.common

import androidx.fragment.app.Fragment

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/9 21:05
 *
 * 介绍：CoTabInfo
 */
interface CoTabInfo<Color : Comparable<Color>> {
	val fragment: Class<out Fragment>?
}