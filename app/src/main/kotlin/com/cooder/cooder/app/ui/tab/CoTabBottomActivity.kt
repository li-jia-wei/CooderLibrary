package com.cooder.cooder.app.ui.tab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.cooder.cooder.app.R
import com.cooder.cooder.app.databinding.ActivityCoTabBottomBinding
import com.cooder.cooder.library.util.CoDisplayUtil
import com.cooder.cooder.library.util.expends.hintStatusBar
import com.cooder.cooder.ui.tab.bottom.CoTabBottomInfo
import com.cooder.cooder.ui.tab.common.CoTabLayout

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
		hintStatusBar()
		
		initTabBottom()
	}
	
	private fun initTabBottom() {
		binding.tabBottomLayout.setTabAlpha(0.9F)
		val defaultColor = ContextCompat.getColor(this, R.color.tab_default)
		val tintColor = ContextCompat.getColor(this, R.color.tab_tint)
		
		val bottomInfoList = mutableListOf<CoTabBottomInfo<*>>()
		val homeInfo = CoTabBottomInfo(
			"首页",
			"font/alibaba_iconfont.ttf",
			getString(R.string.ic_alibaba_home),
			getString(R.string.ic_alibaba_home_fill),
			defaultColor,
			tintColor
		)
		bottomInfoList += homeInfo
		
		val categoryProductsInfo = CoTabBottomInfo(
			"分类",
			"font/alibaba_iconfont.ttf",
			getString(R.string.ic_alibaba_category_products),
			getString(R.string.ic_alibaba_category_products_fill),
			defaultColor,
			tintColor
		)
		bottomInfoList += categoryProductsInfo
		
		val computerBitmap = AppCompatResources.getDrawable(this, R.drawable.ic_computer)!!.toBitmap()
		val computerInfo = CoTabBottomInfo<String>(computerBitmap, computerBitmap)
		bottomInfoList += computerInfo
		
		val addInfo = CoTabBottomInfo(
			"购物车",
			"font/alibaba_iconfont.ttf",
			getString(R.string.ic_alibaba_cart_empty),
			getString(R.string.ic_alibaba_cart_empty_fill),
			defaultColor,
			tintColor
		)
		bottomInfoList += addInfo
		
		val userAccountInfo = CoTabBottomInfo(
			"我的",
			"font/alibaba_iconfont.ttf",
			getString(R.string.ic_alibaba_user_account),
			getString(R.string.ic_alibaba_user_account_fill),
			defaultColor,
			tintColor
		)
		bottomInfoList += userAccountInfo
		
		binding.tabBottomLayout.inflateInfo(bottomInfoList)
		binding.tabBottomLayout.addTabSelectedChangeListener(object : CoTabLayout.OnTabSelectedListener<CoTabBottomInfo<*>> {
			override fun onTabSelectedChange(index: Int, prevInfo: CoTabBottomInfo<*>?, nextInfo: CoTabBottomInfo<*>) {
			
			}
		})
		binding.tabBottomLayout.selectTabInfo(homeInfo)
		binding.tabBottomLayout.findTab(bottomInfoList[2])?.resetHeight(CoDisplayUtil.dp2px(48))
	}
}