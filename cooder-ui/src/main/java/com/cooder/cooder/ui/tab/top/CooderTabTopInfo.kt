package com.cooder.cooder.ui.tab.top

import android.graphics.Bitmap
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

/**
 * 项目：CooderProject
 *
 * 作者：李佳伟
 *
 * 创建：2022/10/7 18:38
 *
 * 介绍：CooderTabTopInfo
 */
class CooderTabTopInfo<Color : Comparable<Color>> {
	
	enum class TabType {
		TEXT, BITMAP, ICON, VALUE_RES
	}
	
	var tabType: TabType? = null
		private set
	
	var fragment: Class<out Fragment>? = null
		private set
	
	var name: String? = null
		private set
	
	var defaultBitmap: Bitmap? = null
		private set
	
	var selectedBitmap: Bitmap? = null
		private set
	
	var iconFont: String? = null
		private set
	
	var defaultIconName: String? = null
		private set
	
	var selectedIconName: String? = null
		private set
	
	var defaultColor: Color? = null
		private set
	
	var tintColor: Color? = null
		private set
	
	@StringRes
	var nameId: Int = -1
		private set
	
	@StringRes
	var defaultIconId: Int = -1
		private set
	
	@StringRes
	var selectedIconId: Int = -1
		private set
	
	@ColorRes
	var defaultColorId: Int = -1
		private set
	
	@ColorRes
	var tintColorId: Int = -1
		private set
	
	/**
	 * Text
	 * @param name 名称
	 * @param defaultColor 默认的颜色
	 * @param tintColor 选中的颜色
	 * @param fragment 对应的Fragment的Class
	 */
	@JvmOverloads
	constructor(name: String, defaultColor: Color, tintColor: Color, fragment: Class<out Fragment>? = null) {
		this.name = name
		this.defaultColor = defaultColor
		this.tintColor = tintColor
		this.fragment = fragment
		this.tabType = TabType.TEXT
	}
	
	/**
	 * Bitmap - 无名称
	 * @param defaultBitmap 默认的Bitmap
	 * @param selectedBitmap 选中的Bitmap
	 * @param fragment 对应的Fragment的Class
	 */
	@JvmOverloads
	constructor(defaultBitmap: Bitmap, selectedBitmap: Bitmap, fragment: Class<out Fragment>? = null) {
		this.defaultBitmap = defaultBitmap
		this.selectedBitmap = selectedBitmap
		this.fragment = fragment
		this.tabType = TabType.BITMAP
	}
	
	/**
	 * Bitmap - 有名称
	 * @param name 名称
	 * @param defaultBitmap 默认的Bitmap
	 * @param selectedBitmap 选中的Bitmap
	 * @param fragment 对应的Fragment的Class
	 */
	@JvmOverloads
	constructor(name: String, defaultBitmap: Bitmap, selectedBitmap: Bitmap, defaultColor: Color, tintColor: Color, fragment: Class<out Fragment>? = null) {
		this.name = name
		this.defaultBitmap = defaultBitmap
		this.selectedBitmap = selectedBitmap
		this.defaultColor = defaultColor
		this.tintColor = tintColor
		this.fragment = fragment
		this.tabType = TabType.BITMAP
	}
	
	/**
	 * Icon - 无名称
	 * @param iconFont 图标字体库路径
	 * @param defaultIconName 默认字体名称
	 * @param selectedIconName 选中字体名称
	 * @param fragment 对应的Fragment的Class
	 */
	@JvmOverloads
	constructor(iconFont: String, defaultIconName: String, selectedIconName: String, defaultColor: Color, tintColor: Color, fragment: Class<out Fragment>? = null) {
		this.iconFont = iconFont
		this.defaultIconName = defaultIconName
		this.selectedIconName = selectedIconName
		this.defaultColor = defaultColor
		this.tintColor = tintColor
		this.fragment = fragment
		this.tabType = TabType.ICON
	}
	
	/**
	 * Icon - 有名称
	 * @param name 名称
	 * @param iconFont 图标字体库路径
	 * @param defaultIconName 默认字体名称
	 * @param selectedIconName 选中字体名称
	 * @param fragment 对应的Fragment的Class
	 */
	@JvmOverloads
	constructor(name: String, iconFont: String, defaultIconName: String, selectedIconName: String, defaultColor: Color, tintColor: Color, fragment: Class<out Fragment>? = null) {
		this.iconFont = iconFont
		this.defaultIconName = defaultIconName
		this.selectedIconName = selectedIconName
		this.defaultColor = defaultColor
		this.tintColor = tintColor
		this.fragment = fragment
		this.tabType = TabType.ICON
	}
	
	/**
	 * ValueRes  - 无名称
	 * @param iconFont 图标字符库路径
	 * @param defaultIconId 默认字符名称资源Id
	 * @param selectedIconId 选中字符名称资源Id
	 * @param defaultColorId 默认的颜色资源Id
	 * @param tintColorId 选中的颜色资源Id
	 * @param fragment 对应的Fragment的Class
	 */
	@JvmOverloads
	constructor(
		iconFont: String,
		@StringRes defaultIconId: Int,
		@StringRes selectedIconId: Int,
		@ColorRes defaultColorId: Int,
		@ColorRes tintColorId: Int,
		fragment: Class<out Fragment>? = null
	) {
		this.iconFont = iconFont
		this.defaultIconId = defaultIconId
		this.selectedIconId = selectedIconId
		this.defaultColorId = defaultColorId
		this.tintColorId = tintColorId
		this.fragment = fragment
		this.tabType = TabType.VALUE_RES
	}
	
	/**
	 * ValueRes  - 有名称
	 * @param nameId 名称Id
	 * @param iconFont 图标字符库路径
	 * @param defaultIconId 默认字符名称资源Id
	 * @param selectedIconId 选中字符名称资源Id
	 * @param defaultColorId 默认的颜色资源Id
	 * @param tintColorId 选中的颜色资源Id
	 * @param fragment 对应的Fragment的Class
	 */
	@JvmOverloads
	constructor(
		@StringRes nameId: Int,
		iconFont: String,
		@StringRes defaultIconId: Int,
		@StringRes selectedIconId: Int,
		@ColorRes defaultColorId: Int,
		@ColorRes tintColorId: Int,
		fragment: Class<out Fragment>? = null
	) {
		this.nameId = nameId
		this.iconFont = iconFont
		this.defaultIconId = defaultIconId
		this.selectedIconId = selectedIconId
		this.defaultColorId = defaultColorId
		this.tintColorId = tintColorId
		this.fragment = fragment
		this.tabType = TabType.VALUE_RES
	}
}