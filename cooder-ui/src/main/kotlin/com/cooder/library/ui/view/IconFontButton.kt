package com.cooder.library.ui.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatButton
import com.cooder.library.ui.R

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2023/6/25 10:25
 *
 * 介绍：IconFont按钮组件
 *
 * [阿里巴巴图标库默认图标](http://cooder.top:8080/iconfont/alibaba/index.html)
 */
class IconFontButton @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr) {
	
	private companion object {
		private const val TYPEFACE_DEFAULT = "iconfont/alibaba.ttf"
	}
	
	init {
		val typefacePath: String
		if (attrs != null) {
			val array = context.obtainStyledAttributes(attrs, R.styleable.IconFontButton)
			typefacePath = array.getString(R.styleable.IconFontButton_typeface) ?: TYPEFACE_DEFAULT
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