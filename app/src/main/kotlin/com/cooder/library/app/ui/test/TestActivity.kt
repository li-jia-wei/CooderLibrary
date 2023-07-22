package com.cooder.library.app.ui.test

import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.IntRange
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.cooder.library.app.databinding.ActivityTestBinding
import com.cooder.library.library.util.CoViewUtil
import com.cooder.library.library.util.expends.dpInt

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/27 17:00
 *
 * 介绍：TestActivity
 */
class TestActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityTestBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityTestBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		binding.image.loadCorner("https://inews.gtimg.com/newsapp_bt/0/13957280208/1000", 20.dpInt, 411.dpInt, 411.dpInt)
	}
	
	fun ImageView.loadCorner(url: String, @IntRange(from = 0) corner: Int, width: Int, height: Int) {
		if (CoViewUtil.isActivityDestroy(context)) return
		// fix: 需要先裁剪再设置圆角，否则可能会导致被设置的圆角被裁剪
		Glide.with(this)
			.load(url)
			.transform(CenterCrop(), RoundedCorners(corner))
			.override(width, height)
			.into(this)
	}
}