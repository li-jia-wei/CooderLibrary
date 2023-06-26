package com.cooder.cooder.ui.tab.bottom

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.cooder.cooder.ui.R
import com.cooder.cooder.ui.tab.bottom.CoTabBottomInfo.TabType.BITMAP
import com.cooder.cooder.ui.tab.bottom.CoTabBottomInfo.TabType.ICON
import com.cooder.cooder.ui.tab.bottom.CoTabBottomInfo.TabType.VALUE_RES
import com.cooder.cooder.ui.tab.common.CoTab

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/27 19:58
 *
 * 介绍：底部标签组件
 */
class CoTabBottom @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr), CoTab<CoTabBottomInfo<*>> {
	
	private lateinit var tabInfo: CoTabBottomInfo<*>
	private val tabImageView: ImageView
	private val tabIconView: TextView
	private val tabNameView: TextView
	
	init {
		LayoutInflater.from(context).inflate(R.layout.co_tab_bottom, this)
		tabImageView = findViewById(R.id.iv_image)
		tabIconView = findViewById(R.id.tv_icon)
		tabNameView = findViewById(R.id.tv_name)
	}
	
	fun getTabInfo(): CoTabBottomInfo<*> {
		return tabInfo
	}
	
	fun getTabImageView(): ImageView {
		return tabImageView
	}
	
	fun getTabIconView(): TextView {
		return tabIconView
	}
	
	fun getTabNameView(): TextView {
		return tabNameView
	}
	
	/**
	 * 设置数据
	 */
	override fun setTabInfo(info: CoTabBottomInfo<*>) {
		this.tabInfo = info
		inflateInfo(selected = false, init = true)
	}
	
	/**
	 * 填充视图
	 */
	private fun inflateInfo(selected: Boolean, init: Boolean) {
		tabInfo.tabType?.let {
			when (it) {
				BITMAP -> {
					if (init) {
						tabImageView.visibility = VISIBLE
						tabIconView.visibility = GONE
						if (!TextUtils.isEmpty(tabInfo.name)) {
							tabNameView.text = tabInfo.name
						} else {
							tabNameView.visibility = GONE
						}
					}
					if (selected) {
						tabImageView.setImageBitmap(tabInfo.selectedBitmap)
						if (tabNameView.visibility == VISIBLE)
							tabNameView.setTextColor(getTextColor(tabInfo.tintColor))
					} else {
						tabImageView.setImageBitmap(tabInfo.defaultBitmap)
						if (tabNameView.visibility == VISIBLE)
							tabNameView.setTextColor(getTextColor(tabInfo.defaultColor))
					}
				}
				ICON -> {
					if (init) {
						tabImageView.visibility = GONE
						tabIconView.visibility = VISIBLE
						val typeface = Typeface.createFromAsset(context.assets, tabInfo.iconFont)
						tabIconView.typeface = typeface
						if (!TextUtils.isEmpty(tabInfo.name)) {
							tabNameView.text = tabInfo.name
						} else {
							tabNameView.visibility = GONE
						}
					}
					if (selected) {
						tabIconView.text = tabInfo.selectedIconName
						val tintColor = getTextColor(tabInfo.tintColor)
						tabIconView.setTextColor(tintColor)
						tabNameView.setTextColor(tintColor)
					} else {
						tabIconView.text = tabInfo.defaultIconName
						val defaultColor = getTextColor(tabInfo.defaultColor)
						tabIconView.setTextColor(defaultColor)
						tabNameView.setTextColor(defaultColor)
					}
				}
				VALUE_RES -> {
					if (init) {
						tabImageView.visibility = GONE
						tabIconView.visibility = VISIBLE
						val typeface = Typeface.createFromAsset(context.assets, tabInfo.iconFont)
						tabIconView.typeface = typeface
						if (tabInfo.nameId != -1) {
							tabNameView.setText(tabInfo.nameId)
						} else {
							tabNameView.visibility = GONE
						}
					}
					if (selected) {
						tabIconView.setText(tabInfo.selectedIconId)
						val tintColor = ContextCompat.getColor(context, tabInfo.tintColorId)
						tabNameView.setTextColor(tintColor)
						tabIconView.setTextColor(tintColor)
					} else {
						tabIconView.setText(tabInfo.defaultIconId)
						val defaultColor = ContextCompat.getColor(context, tabInfo.defaultColorId)
						tabNameView.setTextColor(defaultColor)
						tabIconView.setTextColor(defaultColor)
					}
				}
			}
		}
	}
	
	override fun resetHeight(height: Int) {
		val params = layoutParams
		params.height = height
		layoutParams = params
		tabNameView.visibility = GONE
	}
	
	override fun onTabSelectedChange(index: Int, prevInfo: CoTabBottomInfo<*>?, nextInfo: CoTabBottomInfo<*>) {
		if (prevInfo != tabInfo && nextInfo != tabInfo || prevInfo == nextInfo) {
			return
		}
		inflateInfo(selected = prevInfo != tabInfo, init = false)
	}
	
	/**
	 * 获取颜色
	 * @param C String or Int
	 */
	@ColorInt
	private fun <C> getTextColor(color: C): Int = if (color is String) {
		Color.parseColor(color)
	} else {
		color as Int
	}
}