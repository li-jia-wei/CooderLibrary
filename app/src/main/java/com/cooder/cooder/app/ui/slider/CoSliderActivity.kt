package com.cooder.cooder.app.ui.slider

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.cooder.cooder.app.R
import com.cooder.cooder.library.util.expends.dpInt
import com.cooder.cooder.library.util.expends.readText
import com.cooder.cooder.library.util.setStatusBar
import com.cooder.cooder.ui.slider.*
import com.google.gson.Gson

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/12/24 16:05
 *
 * 介绍：CooderSliderActivity
 */
class CoSliderActivity : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_co_slider)
		setStatusBar(true, Color.WHITE)
		
		val sliderJson = assets.readText("json/cooder_slider.json")
		val sliderMo = Gson().fromJson(sliderJson, SliderMo::class.java)
		
		val sliderView: CooderSliderView = findViewById(R.id.slider_view)
		sliderView.bindMenuView(sliderMo.data.size, callback = object : MenuItemCallback {
			
			override fun onBindView(holder: MenuItemViewHolder, position: Int) {
				holder.menuItemTitle.text = sliderMo.data[position].menu
			}
			
			override fun onMenuItemClickListener(holder: MenuItemViewHolder, position: Int) {
				sliderView.bindContentView(sliderMo.data[position].content.size, callback = object : ContentItemCallback {
					private val data = sliderMo.data[position]
					override fun onBindView(holder: ContentItemViewHolder, position: Int) {
						val content = data.content[position]
						Glide.with(holder.contentItemImage)
							.load(ColorDrawable(Color.parseColor(content.background)))
							.transform(CenterCrop(), RoundedCorners(10.dpInt))
							.into(holder.contentItemImage)
						holder.contentItemTitle.text = content.name
					}
					
					override fun onItemClickListener(holder: ContentItemViewHolder, position: Int) {
						val text = String.format("%s %s", data.menu, data.content[position].name)
						Toast.makeText(this@CoSliderActivity, text, Toast.LENGTH_SHORT).show()
					}
				})
			}
		})
	}
}