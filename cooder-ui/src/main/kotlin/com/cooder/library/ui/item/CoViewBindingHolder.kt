package com.cooder.library.ui.item

import androidx.viewbinding.ViewBinding

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/6/25 01:01
 *
 * 介绍：CoViewBindingHolder
 */
class CoViewBindingHolder<VB : ViewBinding>(
	val binding: VB
) : CoViewHolder(binding.root)