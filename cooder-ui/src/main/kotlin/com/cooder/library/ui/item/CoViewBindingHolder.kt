package com.cooder.library.ui.item

import androidx.viewbinding.ViewBinding

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/3 19:33
 *
 * 介绍：CoViewBindingHolder
 */
class CoViewBindingHolder<VB : ViewBinding>(
	val binding: VB
) : CoViewHolder(binding.root)