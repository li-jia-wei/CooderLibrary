package com.cooder.library.app.ui.tab

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cooder.cooder.app.R
import com.cooder.cooder.app.databinding.ActivityCoTabTopBinding
import com.cooder.cooder.library.util.expends.setStatusBar
import com.cooder.cooder.ui.tab.common.CoTabLayout
import com.cooder.cooder.ui.tab.top.CoTabTopInfo

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/8 21:02
 *
 * 介绍：CooderTabTopActivity
 */
class CoTabTopActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityCoTabTopBinding
	
	private val tabTypes = listOf("热门", "服装", "数码", "鞋子", "零食", "家电", "汽车", "百货", "家具", "装修", "运动")
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityCoTabTopBinding.inflate(layoutInflater)
		setContentView(binding.root)
		setStatusBar(true, Color.WHITE)
		initTabTop()
	}
	
	private fun initTabTop() {
		val infoList = mutableListOf<CoTabTopInfo<*>>()
		val defaultColor = ContextCompat.getColor(this, R.color.tab_default)
		val tintColor = ContextCompat.getColor(this, R.color.tab_tint)
		tabTypes.forEach {
			infoList += CoTabTopInfo(it, defaultColor, tintColor)
		}
		binding.tabTopLayout.inflateInfo(infoList)
		binding.tabTopLayout.addTabSelectedChangeListener(object : CoTabLayout.OnTabSelectedListener<CoTabTopInfo<*>> {
			override fun onTabSelectedChange(index: Int, prevInfo: CoTabTopInfo<*>?, nextInfo: CoTabTopInfo<*>) {
				Toast.makeText(this@CoTabTopActivity, nextInfo.name, Toast.LENGTH_SHORT).show()
			}
		})
		binding.tabTopLayout.selectTabInfo(infoList[0])
	}
}