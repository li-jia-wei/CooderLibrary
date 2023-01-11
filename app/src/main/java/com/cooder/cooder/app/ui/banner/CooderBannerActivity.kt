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
import com.cooder.cooder.library.util.CooderStatusBar
import com.cooder.cooder.ui.banner.CooderBanner
import com.cooder.cooder.ui.banner.core.CooderBannerAdapter
import com.cooder.cooder.ui.banner.core.CooderBannerMo
import com.cooder.cooder.ui.banner.core.IBindAdapter
import com.cooder.cooder.ui.banner.indicator.CircleIndicator
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
		setContentView(R.layout.activity_cooder_banner)
        CooderStatusBar.hintStatusBar(this)
		
		banners += findViewById<CooderBanner>(R.id.banner_circle)
		banners += findViewById<CooderBanner>(R.id.banner_number)
        banners += findViewById<CooderBanner>(R.id.banner_line)
        banners += findViewById<CooderBanner>(R.id.banner_attrs)
		
		autoPlay = findViewById(R.id.auto_play)
		intervalTime = findViewById(R.id.et_interval_time)
		
		initBannerCircle()
		initBannerNumber()
        initBannerLine()
        initBannerAttrs()
		
		autoPlay.isChecked = true
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
			val model = CooderBannerMo(it)
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
                override fun onBind(viewHolder: CooderBannerAdapter.CooderBannerViewHolder, mo: CooderBannerMo, position: Int) {
                    val image: ImageView = viewHolder.findViewById(R.id.iv_image)
                    Glide.with(this@CooderBannerActivity).load(mo.url).into(image)
                    val url: TextView = viewHolder.findViewById(R.id.tv_url)
                    url.text = mo.url
                }
			})
		}
	}
	
	private fun initBannerNumber() {
		val models = mutableListOf<CooderBannerMo>()
		urls.forEach {
			val model = CooderBannerMo(it)
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
                override fun onBind(viewHolder: CooderBannerAdapter.CooderBannerViewHolder, mo: CooderBannerMo, position: Int) {
                    val image: ImageView = viewHolder.findViewById(R.id.iv_image)
                    Glide.with(this@CooderBannerActivity).load(mo.url).into(image)
                    val url: TextView = viewHolder.findViewById(R.id.tv_url)
                    url.text = mo.url
                }
			})
		}
	}
	
	private fun initBannerLine() {
		val models = mutableListOf<CooderBannerMo>()
		urls.forEach {
			val model = CooderBannerMo(it)
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
                override fun onBind(viewHolder: CooderBannerAdapter.CooderBannerViewHolder, mo: CooderBannerMo, position: Int) {
                    val image: ImageView = viewHolder.findViewById(R.id.iv_image)
                    Glide.with(this@CooderBannerActivity).load(mo.url).into(image)
                    val url: TextView = viewHolder.findViewById(R.id.tv_url)
                    url.text = mo.url
                }
            })
        }
    }

    private fun initBannerAttrs() {
        val models = mutableListOf<CooderBannerMo>()
        urls.forEach {
            val model = CooderBannerMo(it)
            models += model
        }
        banners[3].apply {
            setBannerData(R.layout.banner_item_layout, models)
            setBindAdapter(object : IBindAdapter {
                override fun onBind(viewHolder: CooderBannerAdapter.CooderBannerViewHolder, mo: CooderBannerMo, position: Int) {
                    val image: ImageView = viewHolder.findViewById(R.id.iv_image)
                    Glide.with(this@CooderBannerActivity).load(mo.url).into(image)
                    val url: TextView = viewHolder.findViewById(R.id.tv_url)
                    url.text = mo.url
                }
            })
        }
    }
}