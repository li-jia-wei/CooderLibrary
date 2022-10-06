package com.cooder.cooder.ui.tab.bottom

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.cooder.cooder.ui.R
import com.cooder.cooder.ui.tab.bottom.CooderTabBottomInfo.TabType.*
import com.cooder.cooder.ui.tab.common.ICooderTab

/**
 * 项目名称：CooderLibrary
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/9/27 19:58
 *
 * 文件介绍：底部导航组件
 */
class CooderTabBottom @JvmOverloads constructor(
	context: Context,
	attributeSet: AttributeSet? = null,
	defStyleAttr: Int = 0,
) : RelativeLayout(context, attributeSet, defStyleAttr), ICooderTab<CooderTabBottomInfo<*>> {
	
	private lateinit var tabInfo: CooderTabBottomInfo<*>
	private val tabImageView: ImageView
	private val tabIconView: TextView
	private val tabNameView: TextView
	
	init {
		LayoutInflater.from(context).inflate(R.layout.cooder_tab_bottom, this)
		tabImageView = findViewById(R.id.iv_image)
		tabIconView = findViewById(R.id.tv_icon)
		tabNameView = findViewById(R.id.tv_name)
	}
	
	fun getCooderTabInfo(): CooderTabBottomInfo<*> {
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
	override fun setTabInfo(data: CooderTabBottomInfo<*>) {
		this.tabInfo = data
		inflateInfo(selected = false, init = true)
	}
	
	/**
	 * 填充视图
	 */
	private fun inflateInfo(selected: Boolean, init: Boolean) {
		tabInfo.tabType?.apply {
			when (this) {
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
						tabIconView.text = if (TextUtils.isEmpty(tabInfo.selectedIconName)) tabInfo.defaultIconName else tabInfo.selectedIconName
						val tintColor = getTextColor(tabInfo.tintColor)
						tabNameView.setTextColor(tintColor)
						tabIconView.setTextColor(tintColor)
					} else {
						tabIconView.text = tabInfo.defaultIconName
						val defaultColor = getTextColor(tabInfo.defaultColor)
						tabNameView.setTextColor(defaultColor)
						tabIconView.setTextColor(defaultColor)
					}
				}
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
				RES_ID -> {
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
						tabIconView.setText(if (tabInfo.selectedIconId != -1) tabInfo.selectedIconId else tabInfo.defaultIconId)
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
	
	override fun onTabSelectedChange(index: Int, prevInfo: CooderTabBottomInfo<*>?, nextInfo: CooderTabBottomInfo<*>) {
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