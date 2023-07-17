package com.cooder.library.ui.refresh.overview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import com.cooder.library.ui.R

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/13 17:20
 *
 * 介绍：CoRefreshTextOverView
 */
class CoTextOverView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : CoOverView(context, attrs, defStyleAttr) {
	
	private lateinit var hintView: TextView
	private lateinit var rotateView: ImageView
	
	private var visibleHint = context.getString(R.string.refresh_text_overview_visible)
	private var overHint = context.getString(R.string.refresh_text_overview_over)
	private var refreshHint = context.getString(R.string.refresh_text_overview_refresh)
	
	/**
	 * 设置刚下拉时候时的提示文本
	 */
	fun setVisibleHint(@StringRes id: Int) {
		this.visibleHint = context.getString(id)
	}
	
	/**
	 * 超出可刷新距离时的提示文本
	 */
	fun setOverHint(@StringRes id: Int) {
		this.overHint = context.getString(id)
	}
	
	/**
	 * 刷新时候的提示文字
	 */
	fun setRefreshHint(@StringRes id: Int) {
		this.refreshHint = context.getString(id)
	}
	
	override fun init() {
		LayoutInflater.from(context).inflate(R.layout.layout_refresh_overview_text, this, true)
		hintView = findViewById(R.id.tv_hint)
		rotateView = findViewById(R.id.iv_rotate)
	}
	
	override fun onScroll(scrollY: Int, pullRefreshHeight: Int) {
	
	}
	
	override fun onVisible() {
		hintView.text = visibleHint
	}
	
	override fun onOver() {
		hintView.text = overHint
	}
	
	override fun onRefresh() {
		hintView.text = refreshHint
		val rotateAnim = AnimationUtils.loadAnimation(context, R.anim.refresh_text_rotate_anim)
		rotateAnim.interpolator = LinearInterpolator()
		rotateView.startAnimation(rotateAnim)
	}
	
	override fun onFinish() {
		rotateView.clearAnimation()
	}
}