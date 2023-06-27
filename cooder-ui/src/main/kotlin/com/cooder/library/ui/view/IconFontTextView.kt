package com.cooder.library.ui.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatTextView
import com.cooder.library.ui.R

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/16 12:04
 *
 * 介绍：IconFont文本组件
 */
class IconFontTextView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
	
	private companion object {
		private const val TYPEFACE_DEFAULT = "font/iconfont_default.ttf"
	}
	
	init {
		val typefacePath: String
		if (attrs != null) {
			val array = context.obtainStyledAttributes(attrs, R.styleable.IconFontTextView)
			typefacePath = array.getString(R.styleable.IconFontTextView_typeface) ?: TYPEFACE_DEFAULT
			array.recycle()
		} else {
			typefacePath = TYPEFACE_DEFAULT
		}
		this.typeface = Typeface.createFromAsset(context.assets, typefacePath)
	}
	
	fun setTypeface(@StringRes pathId: Int) {
		this.typeface = Typeface.createFromAsset(context.assets, context.getString(pathId))
	}
	
	fun setTypeface(path: String) {
		this.typeface = Typeface.createFromAsset(context.assets, path)
	}
}