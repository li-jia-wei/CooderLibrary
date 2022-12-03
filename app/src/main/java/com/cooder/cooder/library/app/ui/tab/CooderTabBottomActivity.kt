package com.cooder.cooder.library.app.ui.tab

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.cooder.cooder.library.app.R
import com.cooder.cooder.library.util.CooderDisplayUtil
import com.cooder.cooder.ui.tab.bottom.CooderTabBottomInfo
import com.cooder.cooder.ui.tab.bottom.CooderTabBottomLayout
import com.cooder.cooder.ui.tab.common.CooderTabLayout

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/29 10:55
 *
 * 介绍：底部导航Demo
 */
class CooderTabBottomActivity : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_cooder_tab_bottom)
		
		initTabBottom()
	}
	
	private fun initTabBottom() {
		val tabBottomLayout: CooderTabBottomLayout = findViewById(R.id.tab_bottom_layout)
		tabBottomLayout.setTabAlpha(0.9F)
		val defaultColor = ContextCompat.getColor(this, R.color.tab_default)
		val tintColor = ContextCompat.getColor(this, R.color.tab_tint)
		
		val bottomInfoList = mutableListOf<CooderTabBottomInfo<*>>()
		val homeInfo = CooderTabBottomInfo(
			"首页",
			"font/alibaba_iconfont.ttf",
			getString(R.string.ic_alibaba_home),
			getString(R.string.ic_alibaba_home_fill),
			defaultColor,
			tintColor
		)
		bottomInfoList += homeInfo
		
		val categoryProductsInfo = CooderTabBottomInfo(
			"分类",
			"font/alibaba_iconfont.ttf",
			getString(R.string.ic_alibaba_category_products),
			getString(R.string.ic_alibaba_category_products_fill),
			defaultColor,
			tintColor
		)
		bottomInfoList += categoryProductsInfo
		
		val computerBitmap = AppCompatResources.getDrawable(this, R.drawable.ic_computer)!!.toBitmap()
		val computerInfo = CooderTabBottomInfo<String>(computerBitmap, computerBitmap)
		bottomInfoList += computerInfo
		
		val addInfo = CooderTabBottomInfo(
			"购物车",
			"font/alibaba_iconfont.ttf",
			getString(R.string.ic_alibaba_cart_empty),
			getString(R.string.ic_alibaba_cart_empty_fill),
			defaultColor,
			tintColor
		)
		bottomInfoList += addInfo
		
		val userAccountInfo = CooderTabBottomInfo(
			"我的",
			"font/alibaba_iconfont.ttf",
			getString(R.string.ic_alibaba_user_account),
			getString(R.string.ic_alibaba_user_account_fill),
			defaultColor,
			tintColor
		)
		bottomInfoList += userAccountInfo
		
		tabBottomLayout.inflateInfo(bottomInfoList)
		tabBottomLayout.addTabSelectedChangeListener(object : CooderTabLayout.OnTabSelectedListener<CooderTabBottomInfo<*>> {
			override fun onTabSelectedChange(index: Int, prevInfo: CooderTabBottomInfo<*>?, nextInfo: CooderTabBottomInfo<*>) {
			
			}
		})
		tabBottomLayout.defaultSelected(homeInfo)
		tabBottomLayout.findTab(bottomInfoList[2])?.resetHeight(CooderDisplayUtil.dp2px(this, 48))
	}
}