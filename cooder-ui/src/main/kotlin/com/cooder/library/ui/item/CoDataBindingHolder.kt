package com.cooder.library.ui.item

import androidx.databinding.ViewDataBinding

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/3 18:15
 *
 * 介绍：CoDataBindingHolder
 */
class CoDataBindingHolder<out VDB : ViewDataBinding>(
	val binding: VDB
) : CoViewHolder(binding.root)