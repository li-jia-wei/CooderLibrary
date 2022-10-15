package com.cooder.cooder.ui.refresh.overview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.cooder.cooder.library.util.dp
import com.cooder.cooder.ui.refresh.overview.CooderOverView.CooderRefreshState.STATE_INIT

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/11 00:22
 *
 * 介绍：下拉刷新的Overlay视图，可以重载这个类来定义自己的Overlay
 */
abstract class CooderOverView @JvmOverloads constructor(
	context: Context,
	attributeSet: AttributeSet? = null,
	defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {
	
	/**
	 * 刷新状态
	 */
	enum class CooderRefreshState {
		/**
		 * 初始态
		 */
		STATE_INIT,
		
		/**
		 * Header展示的状态
		 */
		STATE_VISIBLE,
		
		/**
		 * 刷新中的状态
		 */
		STATE_REFRESH,
		
		/**
		 * 超出可刷新距离的状态
		 */
		STATE_OVER,
		
		/**
		 * 超出刷新位置松手后的状态
		 */
		STATE_OVER_RELEASE
	}
	
	/**
	 * 当前刷新状态
	 */
	var state = STATE_INIT
	
	companion object {
		/**
		 * 触发下拉刷新需要的最小高度
		 */
		var PULL_REFRESH_HEIGHT = 0
			private set
		
		/**
		 * 最小阻尼
		 */
		const val MIN_DAMP = 1.6F
		
		/**
		 * 最大阻尼
		 */
		const val MAX_DAMP = 2.2F
	}
	
	init {
		preInit()
	}
	
	/**
	 * 预初始化
	 */
	private fun preInit() {
		PULL_REFRESH_HEIGHT = 90.dp.toInt()
		init()
	}
	
	/**
	 * 初始化
	 */
	protected abstract fun init()
	
	/**
	 * 刷新
	 */
	abstract fun onScroll(scrollY: Int, pullRefreshHeight: Int)
	
	/**
	 * 显示出Overlay回调方法
	 */
	abstract fun onVisible()
	
	/**
	 * 超过Overlay，释放就会加载
	 */
	abstract fun onOver()
	
	/**
	 * 开始刷新
	 */
	abstract fun onRefresh()
	
	/**
	 * 加载完成
	 */
	abstract fun onFinish()
}