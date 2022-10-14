package com.cooder.cooder.app.ui.tab

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cooder.cooder.app.R
import com.cooder.cooder.ui.tab.common.CooderTabLayout
import com.cooder.cooder.ui.tab.top.CooderTabTopInfo
import com.cooder.cooder.ui.tab.top.CooderTabTopLayout

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/8 21:02
 *
 * 介绍：CooderTabTopActivity
 */
class CooderTabTopActivity : AppCompatActivity() {
	
	private val tabTypes = listOf("热门", "服装", "数码", "鞋子", "零食", "家电", "汽车", "百货", "家具", "装修", "运动")
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_cooder_tab_top)
		
		initTabTop()
	}
	
	private fun initTabTop() {
		val tabTopLayout: CooderTabTopLayout = findViewById(R.id.tab_top_layout)
		val infoList = mutableListOf<CooderTabTopInfo<*>>()
		val defaultColor = ContextCompat.getColor(this, R.color.tab_default)
		val tintColor = ContextCompat.getColor(this, R.color.tab_tint)
		tabTypes.forEach {
			infoList += CooderTabTopInfo(it, defaultColor, tintColor)
		}
		tabTopLayout.inflateInfo(infoList)
		tabTopLayout.addTabSelectedChangeListener(object : CooderTabLayout.OnTabSelectedListener<CooderTabTopInfo<*>> {
			override fun onTabSelectedChange(index: Int, prevInfo: CooderTabTopInfo<*>?, nextInfo: CooderTabTopInfo<*>) {
				Toast.makeText(this@CooderTabTopActivity, nextInfo.name, Toast.LENGTH_SHORT).show()
			}
		})
		tabTopLayout.defaultSelected(infoList[0])
	}
}