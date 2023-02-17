package com.cooder.cooder.ui.refresh

import android.view.GestureDetector
import android.view.MotionEvent

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/29 17:53
 *
 * 介绍：CoGestureDetectorListener
 */
internal open class CoGestureDetectorListener : GestureDetector.OnGestureListener {
	
	override fun onDown(e: MotionEvent): Boolean {
		return false
	}
	
	override fun onShowPress(e: MotionEvent) {
	
	}
	
	override fun onSingleTapUp(e: MotionEvent): Boolean {
        return false
    }
    
    override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        return false
    }
    
    override fun onLongPress(e: MotionEvent) {
    
    }
    
    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        return false
    }
}