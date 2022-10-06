package com.cooder.cooder.ui.tab.bottom

import android.graphics.Bitmap
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

/**
 * 项目名称：CooderLibrary
 *
 * 作者姓名：李佳伟
 *
 * 创建时间：2022/9/27 19:45
 *
 * 文件介绍：CooderTabBottomInfo
 */
class CooderTabBottomInfo<Color> {
	
	enum class TabType {
		BITMAP, ICON, RES_ID
	}
	
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
	
	var tabType: TabType? = null
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
	 * Bitmap形式
	 * @param sameBitmap 默认和选中一样的Bitmap
	 */
	constructor(sameBitmap: Bitmap) : this(sameBitmap, sameBitmap)
	
	/**
	 * Bitmap形式
	 * @param defaultBitmap 默认的Bitmap
	 * @param selectedBitmap 选中的Bitmap
	 */
	constructor(defaultBitmap: Bitmap, selectedBitmap: Bitmap) {
		this.defaultBitmap = defaultBitmap
		this.selectedBitmap = selectedBitmap
		this.tabType = TabType.BITMAP
	}
	
	/**
	 * Bitmap形式
	 * @param name 名称
	 * @param defaultBitmap 默认的Bitmap
	 * @param selectedBitmap 选中的Bitmap
	 * @param defaultColor 默认的颜色
	 * @param tintColor 选中的颜色
	 */
	constructor(name: String, defaultBitmap: Bitmap, selectedBitmap: Bitmap, defaultColor: Color, tintColor: Color) {
		this.name = name
		this.defaultBitmap = defaultBitmap
		this.selectedBitmap = selectedBitmap
		this.defaultColor = defaultColor
		this.tintColor = tintColor
		this.tabType = TabType.BITMAP
	}
	
	/**
	 * Bitmap形式
	 * @param name 名称
	 * @param defaultBitmap 默认的Bitmap
	 * @param selectedBitmap 选中的Bitmap
	 * @param defaultColor 默认的颜色
	 * @param tintColor 选中的颜色
	 * @param fragment 对应的Fragment的class
	 */
	constructor(name: String, defaultBitmap: Bitmap, selectedBitmap: Bitmap, defaultColor: Color, tintColor: Color, fragment: Class<out Fragment>) {
		this.name = name
		this.defaultBitmap = defaultBitmap
		this.selectedBitmap = selectedBitmap
		this.defaultColor = defaultColor
		this.tintColor = tintColor
		this.fragment = fragment
		this.tabType = TabType.BITMAP
	}
	
	/**
	 * Icon形式
	 * @param iconFont 图标字体库路径
	 * @param sameIcon 默认和选中的字体名称
	 */
	constructor(iconFont: String, sameIcon: String) : this(iconFont, sameIcon, sameIcon)
	
	/**
	 * Icon形式
	 * @param iconFont 图标字体库路径
	 * @param defaultIconName 默认字体名称
	 * @param selectedIconName 选中字体名称
	 */
	constructor(iconFont: String, defaultIconName: String, selectedIconName: String) {
		this.iconFont = iconFont
		this.defaultIconName = defaultIconName
		this.selectedIconName = selectedIconName
		this.tabType = TabType.ICON
	}
	
	/**
	 * Icon形式
	 * @param name 名称
	 * @param iconFont 图标字体库路径
	 * @param defaultIconName 默认字体名称
	 * @param selectedIconName 选中字体名称
	 * @param defaultColor 默认的颜色
	 * @param tintColor 选中的颜色
	 */
	constructor(name: String, iconFont: String, defaultIconName: String, selectedIconName: String, defaultColor: Color, tintColor: Color) {
		this.name = name
		this.iconFont = iconFont
		this.defaultIconName = defaultIconName
		this.selectedIconName = selectedIconName
		this.defaultColor = defaultColor
		this.tintColor = tintColor
		this.tabType = TabType.ICON
	}
	
	/**
	 * Icon形式
	 * @param name 名称
	 * @param iconFont 图标字体库路径
	 * @param defaultIconName 默认字体名称
	 * @param selectedIconName 选中字体名称
	 * @param defaultColor 默认的颜色
	 * @param tintColor 选中的颜色
	 * @param fragment 对应的Fragment的class
	 */
	constructor(name: String, iconFont: String, defaultIconName: String, selectedIconName: String, defaultColor: Color, tintColor: Color, fragment: Class<out Fragment>) {
		this.name = name
		this.iconFont = iconFont
		this.defaultIconName = defaultIconName
		this.selectedIconName = selectedIconName
		this.defaultColor = defaultColor
		this.tintColor = tintColor
		this.fragment = fragment
		this.tabType = TabType.ICON
	}
	
	/**
	 * 字符图标加资源形式
	 * @param nameId 名称的资源Id
	 * @param iconFont 图标字符库路径
	 * @param defaultIconId 默认字符名称资源Id
	 * @param selectIconId 选中字符名称资源Id
	 * @param defaultColorId 默认的颜色资源Id
	 * @param tintColorId 选中的颜色资源Id
	 * @param fragment 对应的Fragment的class
	 */
	constructor(
		@StringRes nameId: Int,
		iconFont: String,
		@StringRes defaultIconId: Int,
		@StringRes selectIconId: Int,
		@ColorRes defaultColorId: Int,
		@ColorRes tintColorId: Int,
		fragment: Class<out Fragment>
	) {
		this.nameId = nameId
		this.iconFont = iconFont
		this.defaultIconId = defaultIconId
		this.selectedIconId = selectIconId
		this.defaultColorId = defaultColorId
		this.tintColorId = tintColorId
		this.fragment = fragment
		this.tabType = TabType.RES_ID
	}
}