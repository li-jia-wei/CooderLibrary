package com.cooder.library.ui.refresh.overview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import com.cooder.library.ui.R

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/5 13:38
 *
 * 介绍：CoRotateOverView
 */
class CoRotateOverView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : CoOverView(context, attrs, defStyleAttr) {
	
	private lateinit var rotateView: ImageView
	
	override fun init() {
		LayoutInflater.from(context).inflate(R.layout.layout_refresh_overview_text, this, true)
		rotateView = findViewById(R.id.iv_rotate)
	}
	
	override fun onScroll(scrollY: Int, pullRefreshHeight: Int) {
	
	}
	
	override fun onVisible() {
	
	}
	
	override fun onOver() {
	
	}
	
	override fun onRefresh() {
		val rotateAnim = AnimationUtils.loadAnimation(context, R.anim.refresh_text_rotate_anim)
		rotateAnim.interpolator = LinearInterpolator()
		rotateView.startAnimation(rotateAnim)
	}
	
	override fun onFinish() {
		rotateView.clearAnimation()
	}
}