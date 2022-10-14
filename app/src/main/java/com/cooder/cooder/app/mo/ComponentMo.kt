package com.cooder.cooder.app.mo

import android.app.Activity

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/27 18:35
 *
 * 介绍：组件Mo
 */
data class ComponentMo(val toActivityClass: Class<out Activity>, val name: String)