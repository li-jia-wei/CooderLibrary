package com.cooder.cooder.app.ui.banner

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.cooder.cooder.app.R
import com.cooder.cooder.ui.banner.CooderBanner
import com.cooder.cooder.ui.banner.core.CooderBannerAdapter
import com.cooder.cooder.ui.banner.core.CooderBannerMo
import com.cooder.cooder.ui.banner.core.IBindAdapter
import com.cooder.cooder.ui.banner.indicator.IndicatorType
import com.cooder.cooder.ui.banner.indicator.LineIndicator
import com.cooder.cooder.ui.banner.indicator.NumberIndicator

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/24 18:39
 *
 * 介绍：CooderBannerActivity2
 */
class CooderBannerActivity : AppCompatActivity() {
	
	private val banners = mutableListOf<CooderBanner>()
	
	@SuppressLint("UseSwitchCompatOrMaterialCode")
	private lateinit var autoPlay: Switch
	private lateinit var intervalTime: EditText
	
	private val urls = listOf(
		"http://10.0.2.2:8080/FileServer/img/1.png",
		"http://10.0.2.2:8080/FileServer/img/2.png",
		"http://10.0.2.2:8080/FileServer/img/3.png",
		"http://10.0.2.2:8080/FileServer/img/4.png",
		"http://10.0.2.2:8080/FileServer/img/5.png",
		"http://10.0.2.2:8080/FileServer/img/6.png",
		"http://10.0.2.2:8080/FileServer/img/7.png",
	)
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_cooder_banner)
		
		banners += findViewById<CooderBanner>(R.id.banner_circle)
		banners += findViewById<CooderBanner>(R.id.banner_number)
		banners += findViewById<CooderBanner>(R.id.banner_line)
		
		autoPlay = findViewById(R.id.auto_play)
		intervalTime = findViewById(R.id.et_interval_time)
		
		initBannerCircle()
		initBannerNumber()
		initBannerLine()
		
		autoPlay.isChecked = false
		autoPlay.setOnCheckedChangeListener { buttonView, isChecked ->
			banners.forEach {
				it.setAutoPlay(isChecked)
			}
		}
		
		intervalTime.addTextChangedListener {
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
		val models = mutableListOf<CooderBannerMo>()
		urls.forEach {
			val model = object : CooderBannerMo() {}
			model.url = it
			models += model
		}
		banners[0].apply {
			setBannerIndicatorType(IndicatorType.CIRCLE)
			setAutoPlay(false)
			setIntervalTime(2000)
			setLoop(true)
			setScrollDuration(500)
			setBannerData(R.layout.banner_item_layout, models)
			setBindAdapter(object : IBindAdapter {
				override fun onBind(viewHolder: CooderBannerAdapter.CooderBannerViewHolder, bannerMo: CooderBannerMo, position: Int) {
					val image: ImageView = viewHolder.findViewById(R.id.iv_image)
					Glide.with(this@CooderBannerActivity).load(bannerMo.url).into(image)
					val url: TextView = viewHolder.findViewById(R.id.tv_url)
					url.text = bannerMo.url
				}
			})
		}
	}
	
	private fun initBannerNumber() {
		val models = mutableListOf<CooderBannerMo>()
		urls.forEach {
			val model = object : CooderBannerMo() {}
			model.url = it
			models += model
		}
		banners[1].apply {
			val numberIndicator = NumberIndicator(context)
			numberIndicator.setGravity(NumberIndicator.RIGHT_BOTTOM)
			setBannerIndicator(numberIndicator)
			setAutoPlay(false)
			setIntervalTime(2000)
			setLoop(true)
			setScrollDuration(500)
			setBannerData(R.layout.banner_item_layout, models)
			setBindAdapter(object : IBindAdapter {
				override fun onBind(viewHolder: CooderBannerAdapter.CooderBannerViewHolder, bannerMo: CooderBannerMo, position: Int) {
					val image: ImageView = viewHolder.findViewById(R.id.iv_image)
					Glide.with(this@CooderBannerActivity).load(bannerMo.url).into(image)
					val url: TextView = viewHolder.findViewById(R.id.tv_url)
					url.text = bannerMo.url
				}
			})
		}
	}
	
	private fun initBannerLine() {
		val models = mutableListOf<CooderBannerMo>()
		urls.forEach {
			val model = object : CooderBannerMo() {}
			model.url = it
			models += model
		}
		banners[2].apply {
			val lineIndicator = LineIndicator(context)
			setBannerIndicator(lineIndicator)
			setAutoPlay(false)
			setIntervalTime(2000)
			setLoop(true)
			setScrollDuration(500)
			setBannerData(R.layout.banner_item_layout, models)
			setBindAdapter(object : IBindAdapter {
				override fun onBind(viewHolder: CooderBannerAdapter.CooderBannerViewHolder, bannerMo: CooderBannerMo, position: Int) {
					val image: ImageView = viewHolder.findViewById(R.id.iv_image)
					Glide.with(this@CooderBannerActivity).load(bannerMo.url).into(image)
					val url: TextView = viewHolder.findViewById(R.id.tv_url)
					url.text = bannerMo.url
				}
			})
		}
	}
}