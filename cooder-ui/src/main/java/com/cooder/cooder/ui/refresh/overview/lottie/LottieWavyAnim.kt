package com.cooder.cooder.ui.refresh.overview.lottie

import android.content.Context
import android.graphics.Typeface
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.cooder.cooder.library.util.dp
import com.cooder.cooder.ui.R

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/14 18:25
 *
 * 介绍：波浪动画
 */
internal class LottieWavyAnim(
	private val context: Context,
	private val root: ViewGroup
) : LottieAnim {
	
	private val dots = mutableListOf<ImageView>()
	private lateinit var hintLine: TextView
	
	init {
		init()
	}
	
	override fun init() {
		LayoutInflater.from(context).inflate(R.layout.cooder_refresh_overview_lottie_wavy, root, true)
		dots += root.findViewById<ImageView>(R.id.dot1)
		dots += root.findViewById<ImageView>(R.id.dot2)
		dots += root.findViewById<ImageView>(R.id.dot3)
		dots += root.findViewById<ImageView>(R.id.dot4)
		dots += root.findViewById<ImageView>(R.id.dot5)
		dots += root.findViewById<ImageView>(R.id.dot6)
		dots += root.findViewById<ImageView>(R.id.dot7)
		hintLine = root.findViewById(R.id.tv_hint_line)
		val typeface = Typeface.createFromAsset(context.assets, "font/alibaba_iconfont.ttf")
		hintLine.typeface = typeface
	}
	
	override fun visible() {
		hintLine.text = context.getString(R.string.refresh_lottie_wavy_visible)
	}
	
	override fun over() {
		hintLine.text = context.getString(R.string.refresh_lottie_wavy_over)
	}
	
	override fun start() {
		hintLine.text = context.getString(R.string.refresh_lottie_wavy_refresh)
		dots.forEachIndexed { i, iv ->
			val translate = TranslateAnimation(0F, 0F, 0F, (-28).dp)
			translate.duration = 350
			translate.repeatMode = Animation.REVERSE
			translate.repeatCount = Animation.INFINITE
			Handler(Looper.myLooper()!!).postDelayed({
				iv.startAnimation(translate)
			}, i * 100L)
		}
	}
	
	override fun stop() {
		dots.forEachIndexed { i, tv ->
			tv.clearAnimation()
		}
	}
}