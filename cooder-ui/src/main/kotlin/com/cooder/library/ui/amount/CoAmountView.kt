package com.cooder.library.ui.amount

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.TextView
import com.cooder.library.library.log.CoLog
import com.cooder.library.library.util.CoMainHandler
import com.cooder.library.ui.R
import com.cooder.library.ui.view.IconFontButton
import java.util.Timer
import java.util.TimerTask

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/7/12 21:58
 *
 * 介绍：计数器组件
 */
class CoAmountView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
	
	private val attr = AttrParse.parseAttr(context, attrs, defStyleAttr)
	private val btnAttr = attr.btnAttr
	private val amountAttr = attr.amountAttr
	private val valueAttr = attr.valueAttr
	
	private lateinit var increaseBtn: IconFontButton
	private lateinit var decreaseBtn: IconFontButton
	private lateinit var amountView: TextView
	
	private var amount = valueAttr.value
	private var amountChangeListener: ((amount: Int) -> Unit)? = null
	
	private var isLongPressed = false
	private var timer: Timer? = null
	
	/**
	 * 长按增加按钮任务
	 */
	private val increaseLongPressedRunnable = Runnable {
		isLongPressed = true
		timer?.cancel()
		if (amount < valueAttr.maxValue) {
			timer = Timer()
			timer!!.schedule(object : TimerTask() {
				override fun run() {
					CoMainHandler.post {
						CoLog.i("+")
						increase()
					}
				}
			}, 0L, 150L)
		}
	}
	
	/**
	 * 长按减少按钮任务
	 */
	private val decreaseLongPressedRunnable = Runnable {
		isLongPressed = true
		timer?.cancel()
		if (amount > valueAttr.minValue) {
			timer = Timer()
			timer!!.schedule(object : TimerTask() {
				override fun run() {
					CoMainHandler.post {
						CoLog.i("-")
						decrease()
					}
				}
			}, 0L, 150L)
		}
	}
	
	init {
		orientation = HORIZONTAL
		gravity = Gravity.CENTER
		
		initView()
	}
	
	fun setAmountChangeListener(amountChangeListener: (amount: Int) -> Unit) {
		this.amountChangeListener = amountChangeListener
	}
	
	@SuppressLint("ClickableViewAccessibility")
	private fun initView() {
		increaseBtn = generateBtn()
		increaseBtn.setText(R.string.ic_add)
		
		decreaseBtn = generateBtn()
		decreaseBtn.setText(R.string.ic_reduce)
		
		amountView = generateAmountView()
		amountView.text = amount.toString()
		
		increaseBtn.setOnTouchListener { v, event ->
			when (event.action) {
				MotionEvent.ACTION_DOWN -> {
					CoMainHandler.postDelay(800, increaseLongPressedRunnable)
					true
				}
				MotionEvent.ACTION_UP -> {
					if (!isLongPressed) {
						increase()
					}
					timer?.cancel()
					isLongPressed = false
					CoMainHandler.remove(increaseLongPressedRunnable)
					true
				}
				else -> false
			}
		}
		
		decreaseBtn.setOnTouchListener { v, event ->
			when (event.action) {
				MotionEvent.ACTION_DOWN -> {
					CoMainHandler.postDelay(800, decreaseLongPressedRunnable)
					true
				}
				MotionEvent.ACTION_UP -> {
					if (!isLongPressed) {
						decrease()
					}
					timer?.cancel()
					isLongPressed = false
					CoMainHandler.remove(decreaseLongPressedRunnable)
					true
				}
				else -> false
			}
		}
		
		addView(decreaseBtn)
		addView(amountView)
		addView(increaseBtn)
	}
	
	/**
	 * 减1
	 */
	private fun increase() {
		if (amount < valueAttr.maxValue) {
			amount++
			amountView.text = amount.toString()
			this.amountChangeListener?.invoke(amount)
		}
		if (amount == valueAttr.maxValue) {
			timer?.cancel()
		}
	}
	
	/**
	 * 加1
	 */
	private fun decrease() {
		if (amount > valueAttr.minValue) {
			amount--
			amountView.text = amount.toString()
			this.amountChangeListener?.invoke(amount)
		}
		if (amount == valueAttr.minValue) {
			timer?.cancel()
		}
	}
	
	/**
	 * 生成按钮
	 */
	private fun generateBtn(): IconFontButton {
		val button = IconFontButton(context)
		button.includeFontPadding = false
		button.setTextColor(btnAttr.textColor)
		button.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnAttr.textSize.toFloat())
		button.background = btnAttr.background
		button.gravity = Gravity.CENTER
		button.layoutParams = LayoutParams(btnAttr.size, btnAttr.size)
		return button
	}
	
	/**
	 * 生成计数器
	 */
	private fun generateAmountView(): TextView {
		val textView = TextView(context)
		textView.setTextColor(amountAttr.textColor)
		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, amountAttr.textSize.toFloat())
		textView.background = amountAttr.background
		textView.includeFontPadding = false
		textView.gravity = Gravity.CENTER
		val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
		params.marginStart = amountAttr.margin
		params.marginEnd = amountAttr.margin
		textView.layoutParams = params
		textView.minWidth = amountAttr.size
		return textView
	}
}