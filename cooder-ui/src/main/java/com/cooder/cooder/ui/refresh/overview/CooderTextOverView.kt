package com.cooder.cooder.ui.refresh.overview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.cooder.cooder.ui.R

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/13 17:20
 *
 * 介绍：CooderRefreshTextOverView
 */
class CooderTextOverView @JvmOverloads constructor(
	context: Context,
	attributeSet: AttributeSet? = null,
	defStyleAttr: Int = 0
) : CooderOverView(context, attributeSet, defStyleAttr) {
	
	private lateinit var hintView: TextView
	private lateinit var rotateView: ImageView
	
	override fun init() {
		LayoutInflater.from(context).inflate(R.layout.cooder_refresh_text_overview, this, true)
		hintView = findViewById(R.id.tv_hint)
		rotateView = findViewById(R.id.iv_rotate)
	}
	
	override fun onScroll(scrollY: Int, pullRefreshHeight: Int) {
	
	}
	
	override fun onVisible() {
		hintView.setText(R.string.refresh_text_overview_visible)
	}
	
	override fun onOver() {
		hintView.setText(R.string.refresh_text_overview_over)
	}
	
	override fun onRefresh() {
		hintView.setText(R.string.refresh_text_overview_refresh)
		val rotateAnim = AnimationUtils.loadAnimation(context, R.anim.cooder_refresh_text_rotate_anim)
		rotateAnim.interpolator = LinearInterpolator()
		rotateView.startAnimation(rotateAnim)
	}
	
	override fun onFinish() {
		rotateView.clearAnimation()
	}
}