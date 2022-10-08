package com.cooder.cooder.ui.tab.top

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.cooder.cooder.ui.R
import com.cooder.cooder.ui.tab.common.ICooderTab
import com.cooder.cooder.ui.tab.top.CooderTabTopInfo.TabType.*

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/7 20:06
 *
 * 介绍：顶部导航组件
 */
class CooderTabTop @JvmOverloads constructor(
	context: Context,
	attributeSet: AttributeSet? = null,
	defStyleAttr: Int = 0,
) : LinearLayout(context, attributeSet, defStyleAttr), ICooderTab<CooderTabTopInfo<*>> {
	
	private lateinit var tabInfo: CooderTabTopInfo<*>
	private val tabImageView: ImageView
	private val tabIconView: TextView
	private val tabNameView: TextView
	private val indicator: View
	
	init {
		LayoutInflater.from(context).inflate(R.layout.cooder_tab_top, this)
		this.tabImageView = findViewById(R.id.iv_image)
		this.tabIconView = findViewById(R.id.tv_icon)
		this.tabNameView = findViewById(R.id.tv_name)
		this.indicator = findViewById(R.id.indicator)
	}
	
	fun getTabInfo(): CooderTabTopInfo<*> {
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
	
	fun getIndicator(): View {
		return indicator
	}
	
	override fun setTabInfo(info: CooderTabTopInfo<*>) {
		this.tabInfo = info
		inflateInfo(selected = false, init = true)
	}
	
	override fun resetHeight(height: Int) {
		val params = layoutParams
		params.height = height
		layoutParams = params
		tabNameView.visibility = GONE
	}
	
	override fun onTabSelectedChange(index: Int, prevInfo: CooderTabTopInfo<*>?, nextInfo: CooderTabTopInfo<*>) {
		if (prevInfo != tabInfo && nextInfo != tabInfo || prevInfo == nextInfo) {
			return
		}
		inflateInfo(selected = prevInfo != tabInfo, init = false)
	}
	
	private fun inflateInfo(selected: Boolean, init: Boolean) {
		tabInfo.tabType?.also {
			when (it) {
				TEXT -> {
					if (init) {
						tabImageView.visibility = GONE
						tabIconView.visibility = GONE
						if (!TextUtils.isEmpty(tabInfo.name)) {
							tabNameView.visibility = VISIBLE
							tabNameView.text = tabInfo.name
						}
					}
					if (selected) {
						indicator.visibility = VISIBLE
						tabNameView.setTextColor(getTextColor(tabInfo.tintColor))
					} else {
						indicator.visibility = GONE
						tabNameView.setTextColor(getTextColor(tabInfo.defaultColor))
					}
				}
				BITMAP -> {
					if (init) {
						tabImageView.visibility = VISIBLE
						tabIconView.visibility = GONE
						if (!TextUtils.isEmpty(tabInfo.name)) {
							tabNameView.visibility = VISIBLE
							tabNameView.text = tabInfo.name
						} else {
							tabNameView.visibility = GONE
						}
					}
					if (selected) {
						indicator.visibility = VISIBLE
						tabImageView.setImageBitmap(tabInfo.selectedBitmap)
						if (tabNameView.visibility == VISIBLE)
							tabNameView.setTextColor(getTextColor(tabInfo.tintColor))
					} else {
						indicator.visibility = GONE
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
							tabNameView.visibility = VISIBLE
							tabNameView.text = tabInfo.name
						} else {
							tabNameView.visibility = GONE
						}
					}
					if (selected) {
						indicator.visibility = VISIBLE
						tabIconView.text = tabInfo.selectedIconName
						val tintColor = getTextColor(tabInfo.tintColor)
						tabIconView.setTextColor(tintColor)
						tabNameView.setTextColor(tintColor)
					} else {
						indicator.visibility = GONE
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
							tabNameView.visibility = VISIBLE
							tabNameView.setText(tabInfo.nameId)
						} else {
							tabNameView.visibility = GONE
						}
					}
					if (selected) {
						indicator.visibility = VISIBLE
						tabIconView.setText(tabInfo.selectedIconId)
						val tintColor = ContextCompat.getColor(context, tabInfo.tintColorId)
						tabNameView.setTextColor(tintColor)
						tabIconView.setTextColor(tintColor)
					} else {
						indicator.visibility = GONE
						tabIconView.setText(tabInfo.defaultIconId)
						val defaultColor = ContextCompat.getColor(context, tabInfo.defaultColorId)
						tabNameView.setTextColor(defaultColor)
						tabIconView.setTextColor(defaultColor)
					}
				}
			}
		}
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