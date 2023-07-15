package com.cooder.library.app.ui.tab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.cooder.library.app.CoTabViewAdapter
import com.cooder.library.app.R
import com.cooder.library.app.databinding.ActivityCoTabBottomBinding
import com.cooder.library.library.util.expends.hideStatusBar
import com.cooder.library.ui.tab.bottom.CoTabBottomInfo
import com.cooder.library.ui.tab.common.CoTabLayout

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/29 10:55
 *
 * 介绍：底部导航Demo
 */
class CoTabBottomActivity : AppCompatActivity() {
	
	private lateinit var binding: ActivityCoTabBottomBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityCoTabBottomBinding.inflate(layoutInflater)
		setContentView(binding.root)
		hideStatusBar()
		
		initTabBottom()
	}
	
	private fun initTabBottom() {
		binding.tabBottomLayout.setTabBottomAlpha(0.9F)
		val defaultColor = ContextCompat.getColor(this, R.color.tab_default)
		val tintColor = ContextCompat.getColor(this, R.color.tab_tint)
		
		val bottomInfoList = mutableListOf<CoTabBottomInfo<*>>()
		val homeInfo = CoTabBottomInfo(
			"首页",
			"iconfont/alibaba.ttf",
			getString(R.string.ic_home_home),
			getString(R.string.ic_home_home_fill),
			defaultColor,
			tintColor,
			HomeFragment::class.java
		)
		bottomInfoList += homeInfo
		
		val userAccountInfo = CoTabBottomInfo(
			"我的",
			"iconfont/alibaba.ttf",
			getString(R.string.ic_user_account),
			getString(R.string.ic_user_account_fill),
			defaultColor,
			tintColor,
			ProfileFragment::class.java
		)
		bottomInfoList += userAccountInfo
		binding.tabBottomLayout.inflateInfo(bottomInfoList)
		val adapter = CoTabViewAdapter(supportFragmentManager, bottomInfoList)
		binding.fragmentTabView.adapter = adapter
		binding.tabBottomLayout.addTabSelectedChangeListener(object : CoTabLayout.OnTabSelectedListener<CoTabBottomInfo<*>> {
			override fun onTabSelectedChange(index: Int, prevInfo: CoTabBottomInfo<*>?, nextInfo: CoTabBottomInfo<*>) {
				binding.fragmentTabView.setCurrentItem(index)
			}
		})
		binding.tabBottomLayout.selectTabInfo(homeInfo)
	}
}