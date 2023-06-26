package com.cooder.cooder.ui.item

import androidx.databinding.ViewDataBinding

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/6/25 01:01
 *
 * 介绍：CoViewDataBindingHolder
 */
class CoViewDataBindingHolder<VDB : ViewDataBinding>(val binding: VDB) : CoViewHolder(binding.root)