package com.cooder.library.app.ui.banner

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.cooder.library.app.R
import com.cooder.library.app.databinding.ActivityCoBannerBinding
import com.cooder.library.library.util.expends.hintStatusBar
import com.cooder.library.ui.banner.CoBanner
import com.cooder.library.ui.banner.core.CoBannerAdapter
import com.cooder.library.ui.banner.core.CoBannerMo
import com.cooder.library.ui.banner.core.IBindAdapter
import com.cooder.library.ui.banner.indicator.CircleIndicator
import com.cooder.library.ui.banner.indicator.LineIndicator
import com.cooder.library.ui.banner.indicator.NumberIndicator

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/24 18:39
 *
 * 介绍：CooderBannerActivity2
 */
class CoBannerActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityCoBannerBinding
	
	private val banners = mutableListOf<CoBanner>()
	
	private val urls = listOf(
		"https://unsplash.com/photos/mL0y6_AJXJU/download?ixid=MnwxMjA3fDB8MXxhbGx8fHx8fHx8fHwxNjY5NzIxNTQx&force=true&w=640",
		"https://unsplash.com/photos/j_puSkFWmPI/download?ixid=MnwxMjA3fDB8MXxhbGx8fHx8fHx8fHwxNjY5NzIxNzMy&force=true&w=640",
		"https://unsplash.com/photos/mrL7QWWkciE/download?ixid=MnwxMjA3fDB8MXxhbGx8fHx8fHx8fHwxNjY5NzIxNzQz&force=true&w=640",
		"https://unsplash.com/photos/2HMCW78yOZA/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8MjB8fHNtYWxsfGVufDB8fHx8MTY2OTY3MTc0OQ&force=true&w=640",
		"https://unsplash.com/photos/1sAGLbhwaEE/download?ixid=MnwxMjA3fDB8MXxhbGx8fHx8fHx8fHwxNjY5NzIxODY2&force=true&w=640",
		"https://unsplash.com/photos/Tws17PwytpA/download?ixid=MnwxMjA3fDB8MXxhbGx8fHx8fHx8fHwxNjY5NzIxODAy&force=true&w=640",
		"https://unsplash.com/photos/hK1WyD2IaDc/download?ixid=MnwxMjA3fDB8MXxzZWFyY2h8Mjd8fHNtYWxsfGVufDB8fHx8MTY2OTY3MTc2OQ&force=true&w=640",
	)
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityCoBannerBinding.inflate(layoutInflater)
		setContentView(binding.root)
		hintStatusBar()
		
		banners += binding.bannerCircle
		banners += binding.bannerNumber
		banners += binding.bannerLine
		banners += binding.bannerAttrs
		
		initBannerCircle()
		initBannerNumber()
		initBannerLine()
		initBannerAttrs()
		
		binding.autoPlay.isChecked = true
		binding.autoPlay.setOnCheckedChangeListener { _, isChecked ->
			banners.forEach {
				it.setAutoPlay(isChecked)
			}
		}
		
		binding.etIntervalTime.addTextChangedListener {
			if (it == null) return@addTextChangedListener
			val time: Int = if (it.length >= 3) {
				val t = it.toString().toInt()
				if (t < 200) 200 else if (t > 10000) 10000 else t
			} else 2000
			banners.forEach {
				it.setIntervalTime(time)
			}
		}
	}
	
	private fun initBannerCircle() {
		val models = mutableListOf<CoBannerMo>()
		urls.forEach {
			val model = CoBannerMo(it)
			models += model
		}
		banners[0].apply {
			setBannerIndicator(CircleIndicator(context, size = CircleIndicator.SMALL))
			setAutoPlay(true)
			setIntervalTime(2000)
			setLoop(true)
			setScrollDuration(500)
			setBannerData(R.layout.banner_item_layout, models)
			setBindAdapter(object : IBindAdapter {
				override fun onBind(viewHolder: CoBannerAdapter.CoBannerViewHolder, mo: CoBannerMo, position: Int) {
					val image: ImageView = viewHolder.findViewById(R.id.iv_image)
					Glide.with(this@CoBannerActivity).load(mo.url).into(image)
					val url: TextView = viewHolder.findViewById(R.id.tv_url)
					url.text = mo.url
				}
			})
		}
	}
	
	private fun initBannerNumber() {
		val models = mutableListOf<CoBannerMo>()
		urls.forEach {
			val model = CoBannerMo(it)
			models += model
		}
		banners[1].apply {
			val numberIndicator = NumberIndicator(context)
			numberIndicator.setGravity(NumberIndicator.RIGHT_BOTTOM)
			setBannerIndicator(numberIndicator)
			setAutoPlay(true)
			setIntervalTime(2000)
			setLoop(true)
			setScrollDuration(500)
			setBannerData(R.layout.banner_item_layout, models)
			setBindAdapter(object : IBindAdapter {
				override fun onBind(viewHolder: CoBannerAdapter.CoBannerViewHolder, mo: CoBannerMo, position: Int) {
					val image: ImageView = viewHolder.findViewById(R.id.iv_image)
					Glide.with(this@CoBannerActivity).load(mo.url).into(image)
					val url: TextView = viewHolder.findViewById(R.id.tv_url)
					url.text = mo.url
				}
			})
		}
	}
	
	private fun initBannerLine() {
		val models = mutableListOf<CoBannerMo>()
		urls.forEach {
			val model = CoBannerMo(it)
			models += model
		}
		banners[2].apply {
			val lineIndicator = LineIndicator(context)
			setBannerIndicator(lineIndicator)
			setAutoPlay(true)
			setIntervalTime(2000)
			setLoop(true)
			setScrollDuration(500)
			setBannerData(R.layout.banner_item_layout, models)
			setBindAdapter(object : IBindAdapter {
				override fun onBind(viewHolder: CoBannerAdapter.CoBannerViewHolder, mo: CoBannerMo, position: Int) {
					val image: ImageView = viewHolder.findViewById(R.id.iv_image)
					Glide.with(this@CoBannerActivity).load(mo.url).into(image)
					val url: TextView = viewHolder.findViewById(R.id.tv_url)
					url.text = mo.url
				}
			})
		}
	}
	
	private fun initBannerAttrs() {
		val models = mutableListOf<CoBannerMo>()
		urls.forEach {
			val model = CoBannerMo(it)
			models += model
		}
		banners[3].apply {
			setBannerData(R.layout.banner_item_layout, models)
			setBindAdapter(object : IBindAdapter {
				override fun onBind(viewHolder: CoBannerAdapter.CoBannerViewHolder, mo: CoBannerMo, position: Int) {
					val image: ImageView = viewHolder.findViewById(R.id.iv_image)
					Glide.with(this@CoBannerActivity).load(mo.url).into(image)
					val url: TextView = viewHolder.findViewById(R.id.tv_url)
					url.text = mo.url
				}
			})
		}
	}
}