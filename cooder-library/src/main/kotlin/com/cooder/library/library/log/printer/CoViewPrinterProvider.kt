package com.cooder.library.library.log.printer

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cooder.library.library.util.expends.dpInt

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/26 11:55
 *
 * 介绍：日志视图打印提供者
 */
class CoViewPrinterProvider(
	private val rootView: FrameLayout,
	private val recyclerView: RecyclerView,
) {
	private var floatingView: View? = null
	private var logView: FrameLayout? = null
	private var isOpen: Boolean = false
	
	companion object {
		private const val TAG_FLOATING_VIEW = "TAG_FLOATING_VIEW"
		private const val TAG_LOG_VIEW = "TAG_LOG_VIEW"
		
		private const val ALPHA_FLOATING_VIEW = 1F
		private const val ALPHA_LOG_VIEW = 1F
		
		private const val LOG_VIEW_HEIGHT = 250F
		private const val FLOATING_MARGIN_BOTTOM = 20
	}
	
	/**
	 * 开启日志
	 */
	fun showFloatingView() {
		if (rootView.findViewWithTag<View>(TAG_FLOATING_VIEW) != null) {
			return
		}
		val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
		params.gravity = Gravity.BOTTOM or Gravity.END
		params.bottomMargin = FLOATING_MARGIN_BOTTOM.dpInt
		val floatingView = genFloatingView()
		floatingView.setBackgroundColor(Color.BLACK)
		floatingView.alpha = ALPHA_FLOATING_VIEW
		floatingView.tag = TAG_FLOATING_VIEW
		rootView.addView(floatingView, params)
	}
	
	/**
	 * 关闭日志
	 */
	fun closeFloatingView() {
		rootView.removeView(genFloatingView())
	}
	
	/**
	 * 生成FloatingView
	 */
	private fun genFloatingView(): View {
		if (floatingView != null) {
			return floatingView!!
		}
		val floatingView = TextView(rootView.context)
		floatingView.text = "打开日志"
		floatingView.setTextColor(Color.WHITE)
		floatingView.setOnClickListener {
			if (!isOpen) {
				showLogView()
			}
		}
		this.floatingView = floatingView
		return this.floatingView!!
	}
	
	/**
	 * 显示日志系统
	 */
	private fun showLogView() {
		if (rootView.findViewWithTag<View>(TAG_LOG_VIEW) != null) {
			return
		}
		val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, LOG_VIEW_HEIGHT.dpInt)
		params.gravity = Gravity.BOTTOM
		val logView = genLogView()
		logView.tag = TAG_LOG_VIEW
		logView.alpha = ALPHA_LOG_VIEW
		rootView.addView(logView, params)
		isOpen = true
	}
	
	/**
	 * 生成LogView
	 */
	private fun genLogView(): View {
		if (logView != null) {
			return logView!!
		}
		val logView = FrameLayout(rootView.context)
		logView.setBackgroundColor(Color.BLACK)
		logView.addView(recyclerView)
		val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
		params.gravity = Gravity.END
		val closeView = TextView(rootView.context)
		closeView.setOnClickListener {
			closeLogView()
		}
		closeView.text = "关闭日志"
		closeView.setTextColor(Color.WHITE)
		logView.addView(closeView, params)
		this.logView = logView
		return this.logView!!
	}
	
	/**
	 * 关闭日志视图
	 */
	private fun closeLogView() {
		rootView.removeView(genLogView())
		isOpen = false
	}
}