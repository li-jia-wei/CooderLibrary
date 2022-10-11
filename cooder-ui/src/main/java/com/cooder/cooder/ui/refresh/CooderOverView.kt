package com.cooder.cooder.ui.refresh

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

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
		 * 超出可刷新距离的状态
		 */
		STATE_REFRESH,
		
		/**
		 * 超出刷新位置松手后的状态
		 */
		STATE_OVER_RELEASE
	}
	
	/**
	 * 当前刷新状态
	 */
	protected var state = CooderRefreshState.STATE_INIT
	
	companion object {
		/**
		 * 触发下拉刷新需要的最小高度
		 */
		const val PULL_REFRESH_HEIGHT = 0
		
		/**
		 * 最小阻尼
		 */
		const val MIN_DAMP = 1.6F
		
		/**
		 * 最大阻尼
		 */
		const val MAX_DAMP = 2.2F
	}
	
	/**
	 * 初始化
	 */
	abstract fun init()
	
	/**
	 * 刷新
	 */
	protected abstract fun onScroll(scrollY: Int, pullRefreshHeight: Int)
	
	/**
	 * 显示出Overlay回调方法
	 */
	protected abstract fun onVisible()
	
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