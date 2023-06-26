package com.cooder.cooder.library.anim

import android.animation.ValueAnimator

object CoValueAnimator {

    fun <T : Number> start(startValue: T, endValue: T, duration: Long, updateListener: (value: T) -> Unit) {
        val animator = ValueAnimator.ofFloat(startValue.toFloat(), endValue.toFloat())
        animator.addUpdateListener {
            @Suppress("UNCHECKED_CAST")
            updateListener.invoke(it.animatedValue as T)
        }
        animator.duration = duration
        animator.start()
    }
}