package com.cooder.library.ui.tab.bottom

import android.graphics.Bitmap
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.cooder.library.ui.tab.common.CoTabInfo

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/9/27 19:45
 *
 * 介绍：底部标签信息
 */
class CoTabBottomInfo<Color : Comparable<Color>> : CoTabInfo<Color> {
	
	enum class TabType {
		BITMAP,
		ICON,
		VALUE_RES
	}
	
	/**
	 * tab类型
	 */
	var tabType: TabType? = null
		private set
	
	/**
	 * 对应Fragment
	 */
	override var fragment: Class<out Fragment>? = null
		private set
	
	/**
	 * 名称
	 */
	var name: String? = null
		private set
	
	/**
	 * 默认Bitmap
	 */
	var defaultBitmap: Bitmap? = null
		private set
	
	/**
	 * 选中Bitmap
	 */
	var selectedBitmap: Bitmap? = null
		private set
	
	/**
	 * 图标路径
	 */
	var iconFont: String? = null
		private set
	
	/**
	 * 默认的图标Unicode值
	 */
	var defaultIconName: String? = null
		private set
	
	/**
	 * 选中的图标Unicode值
	 */
	var selectedIconName: String? = null
		private set
	
	/**
	 * 默认的颜色，支持Int和String
	 */
	var defaultColor: Color? = null
		private set
	
	/**
	 * 选中的颜色，支持Int和String
	 */
	var tintColor: Color? = null
		private set
	
	/**
	 * 名称 - 资源ID
	 */
	@StringRes
	var nameId: Int = -1
		private set
	
	/**
	 * 默认图标 - 资源ID
	 */
	@StringRes
	var defaultIconId: Int = -1
		private set
	
	/**
	 * 选中图标 - 资源ID
	 */
	@StringRes
	var selectedIconId: Int = -1
		private set
	
	/**
	 * 默认颜色 - 资源ID
	 */
	@ColorRes
	var defaultColorId: Int = -1
		private set
	
	/**
	 * 选中颜色 - 资源ID
	 */
	@ColorRes
	var tintColorId: Int = -1
		private set
	
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
		this.defaultColor = defaultColor
		this.tintColor = tintColor
		this.fragment = fragment
		this.tabType = TabType.BITMAP
	}
	
	/**
	 * Bitmap - 有名称
	 * @param name 名称
	 * @param defaultBitmap 默认的Bitmap
	 * @param selectedBitmap 选中的Bitmap
	 * @param defaultColor 默认的颜色
	 * @param tintColor 选中的颜色
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
	constructor(
		iconFont: String,
		defaultIconName: String,
		selectedIconName: String,
		defaultColor: Color,
		tintColor: Color,
		fragment: Class<out Fragment>? = null
	) {
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
	 * @param defaultColor 默认的颜色
	 * @param tintColor 选中的颜色
	 * @param fragment 对应的Fragment的Class
	 */
	@JvmOverloads
	constructor(
		name: String,
		iconFont: String,
		defaultIconName: String,
		selectedIconName: String,
		defaultColor: Color,
		tintColor: Color,
		fragment: Class<out Fragment>? = null
	) {
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
		fragment: Class<out Fragment>? = null,
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
	
	/**
	 * ValueRes - 有名称
	 * @param nameId 名称的资源Id
	 * @param iconFont 图标字符库路径
	 * @param defaultIconId 默认字符名称资源Id
	 * @param selectIconId 选中字符名称资源Id
	 * @param defaultColorId 默认的颜色资源Id
	 * @param tintColorId 选中的颜色资源Id
	 * @param fragment 对应的Fragment的Class
	 */
	@JvmOverloads
	constructor(
		@StringRes nameId: Int,
		iconFont: String,
		@StringRes defaultIconId: Int,
		@StringRes selectIconId: Int,
		@ColorRes defaultColorId: Int,
		@ColorRes tintColorId: Int,
		fragment: Class<out Fragment>? = null,
	) {
		this.nameId = nameId
		this.iconFont = iconFont
		this.defaultIconId = defaultIconId
		this.selectedIconId = selectIconId
		this.defaultColorId = defaultColorId
		this.tintColorId = tintColorId
		this.fragment = fragment
		this.tabType = TabType.VALUE_RES
	}
}