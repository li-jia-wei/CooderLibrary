package com.cooder.library.ui.search

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.Px
import com.cooder.library.library.util.expends.dpInt
import com.cooder.library.library.util.expends.spInt
import com.cooder.library.ui.R

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2023/6/28 00:58
 *
 * 介绍：AttrParse
 */
internal object AttrsParse {
	
	private val SEARCH_BACKGROUND = R.drawable.shape_search
	private const val SEARCH_HORIZONTAL = 8
	private const val SEARCH_VERTICAL = 8
	private const val SEARCH_CHANGE_INTERVAL = 300
	private const val TEXT_SIZE = 16
	private val TEXT_COLOR = R.color.search_text_color
	private const val TEXT_MAX_LENGTH = 200
	private val NAV_ICON = R.string.ic_left
	private const val NAV_ICON_SIZE = 16
	private val NAV_COLOR = R.color.search_nav_color
	private const val NAV_PADDING = 8
	private const val NAV_ENABLED = true
	private val HINT_ICON = R.string.ic_search
	private const val HINT_ICON_SIZE = 16
	private const val HINT_ICON_ENABLED = true
	private val HINT_TEXT = R.string.search_hint_text
	private const val HINT_TEXT_SIZE = 16
	private const val HINT_PADDING = 8
	private val HINT_COLOR = R.color.search_hint_color
	private const val HINT_GRAVITY = 0
	private val CLEAR_ICON = R.string.ic_close
	private const val CLEAR_SIZE = 11
	private val CLEAR_COLOR = R.color.search_clear_color
	private const val CLEAR_PADDING = 8
	private const val CLEAR_ENABLED = true
	private val CONFIRM_TEXT = R.string.search_confirm_text
	private const val CONFIRM_SIZE = 16
	private val CONFIRM_COLOR = R.color.search_confirm_color
	private const val CONFIRM_PADDING = 8
	private const val CONFIRM_ENABLED = true
	private const val KEYWORD_TEXT_SIZE = 13
	private val KEYWORD_TEXT_COLOR = R.color.white
	private val KEYWORD_BACKGROUND = R.drawable.shape_search_keyword
	private val KEYWORD_ICON = R.string.ic_error
	private const val KEYWORD_ICON_SIZE = 13
	private const val KEYWORD_MAX_WIDTH = 160
	private const val KEYWORD_PADDING = 4
	private const val KEYWORD_ELLIPSIZE = 1
	private const val KEYWORD_ENABLED = true
	
	fun parseAttrs(context: Context, attrs: AttributeSet?, defStyleAttr: Int): Attr {
		val value = TypedValue()
		context.theme.resolveAttribute(R.attr.searchStyle, value, true)
		val defStyleRes = if (value.resourceId != 0) value.resourceId else R.style.CoSearchViewStyle
		val array = context.obtainStyledAttributes(attrs, R.styleable.CoSearchView, defStyleAttr, defStyleRes)
		val searchBackground = array.getResourceId(R.styleable.CoSearchView_searchBackground, SEARCH_BACKGROUND)
		val searchHorizontal = array.getDimensionPixelOffset(R.styleable.CoSearchView_searchHorizontal, SEARCH_HORIZONTAL.dpInt)
		val searchVertical = array.getDimensionPixelOffset(R.styleable.CoSearchView_searchVertical, SEARCH_VERTICAL)
		val searchChangeInterval = array.getInt(R.styleable.CoSearchView_searchChangeInterval, SEARCH_CHANGE_INTERVAL)
		val searchAttr = SearchAttr(searchBackground, searchHorizontal, searchVertical, searchChangeInterval.toLong())
		
		val textSize = array.getDimensionPixelOffset(R.styleable.CoSearchView_textSize, TEXT_SIZE.spInt)
		val textColor = array.getColor(R.styleable.CoSearchView_textColor, context.getColor(TEXT_COLOR))
		val textMaxLength = array.getInt(R.styleable.CoSearchView_textMaxLength, TEXT_MAX_LENGTH)
		val textAttr = TextAttr(textSize, textColor, textMaxLength)
		
		val navIcon = array.getString(R.styleable.CoSearchView_navIcon) ?: context.getString(NAV_ICON)
		val navIconSize = array.getDimensionPixelOffset(R.styleable.CoSearchView_navIconSize, NAV_ICON_SIZE.spInt)
		val navColor = array.getColor(R.styleable.CoSearchView_navColor, context.getColor(NAV_COLOR))
		val navPadding = array.getDimensionPixelOffset(R.styleable.CoSearchView_navPadding, NAV_PADDING.dpInt)
		val navTypeface = array.getString(R.styleable.CoSearchView_navTypeface)
		val navEnabled = array.getBoolean(R.styleable.CoSearchView_navEnabled, NAV_ENABLED)
		val navAttr = NavAttr(navIcon, navIconSize, navColor, navPadding, navTypeface, navEnabled)
		
		val hintIcon = array.getString(R.styleable.CoSearchView_hintIcon) ?: context.getString(HINT_ICON)
		val hintIconSize = array.getDimensionPixelOffset(R.styleable.CoSearchView_hintIconSize, HINT_ICON_SIZE.spInt)
		val hintPadding = array.getDimensionPixelOffset(R.styleable.CoSearchView_hintPadding, HINT_PADDING.dpInt)
		val hintIconEnabled = array.getBoolean(R.styleable.CoSearchView_hintIconEnabled, HINT_ICON_ENABLED)
		val hintText = array.getString(R.styleable.CoSearchView_hintText) ?: context.getString(HINT_TEXT)
		val hintTextSize = array.getDimensionPixelOffset(R.styleable.CoSearchView_hintTextSize, HINT_TEXT_SIZE.spInt)
		val hintTextColor = array.getColor(R.styleable.CoSearchView_hintColor, context.getColor(HINT_COLOR))
		val hintGravity = array.getInt(R.styleable.CoSearchView_hintGravity, HINT_GRAVITY)
		val hintAttr = HintAttr(hintIcon, hintIconSize, hintIconEnabled, hintText, hintTextSize, hintPadding, hintTextColor, hintGravity)
		
		val clearIcon = array.getString(R.styleable.CoSearchView_clearIcon) ?: context.getString(CLEAR_ICON)
		val clearSize = array.getDimensionPixelOffset(R.styleable.CoSearchView_clearSize, CLEAR_SIZE.spInt)
		val clearColor = array.getColor(R.styleable.CoSearchView_clearColor, context.getColor(CLEAR_COLOR))
		val clearPadding = array.getDimensionPixelOffset(R.styleable.CoSearchView_clearPadding, CLEAR_PADDING.dpInt)
		val clearEnabled = array.getBoolean(R.styleable.CoSearchView_clearEnabled, CLEAR_ENABLED)
		val clearAttr = ClearAttr(clearIcon, clearSize, clearColor, clearPadding, clearEnabled)
		
		val confirmIcon = array.getString(R.styleable.CoSearchView_confirmIcon)
		val confirmText = if (confirmIcon == null) array.getString(R.styleable.CoSearchView_confirmText) ?: context.getString(CONFIRM_TEXT) else null
		val confirmSize = array.getDimensionPixelOffset(R.styleable.CoSearchView_confirmSize, CONFIRM_SIZE.spInt)
		val confirmColor = array.getColor(R.styleable.CoSearchView_confirmColor, context.getColor(CONFIRM_COLOR))
		val confirmPadding = array.getDimensionPixelOffset(R.styleable.CoSearchView_confirmPadding, CONFIRM_PADDING.dpInt)
		val confirmTypeface = array.getString(R.styleable.CoSearchView_navTypeface)
		val confirmEnabled = array.getBoolean(R.styleable.CoSearchView_confirmEnabled, CONFIRM_ENABLED)
		val confirmAttr = ConfirmAttr(confirmIcon, confirmText, confirmSize, confirmColor, confirmPadding, confirmTypeface, confirmEnabled)
		
		val keywordTextSize = array.getDimensionPixelOffset(R.styleable.CoSearchView_keywordTextSize, KEYWORD_TEXT_SIZE.spInt)
		val keywordTextColor = array.getColor(R.styleable.CoSearchView_keywordTextColor, context.getColor(KEYWORD_TEXT_COLOR))
		val keywordBackground = array.getResourceId(R.styleable.CoSearchView_keywordBackground, KEYWORD_BACKGROUND)
		val keywordIcon = array.getString(R.styleable.CoSearchView_keywordIcon) ?: context.getString(KEYWORD_ICON)
		val keywordIconSize = array.getDimensionPixelOffset(R.styleable.CoSearchView_keywordIconSize, KEYWORD_ICON_SIZE.spInt)
		val keywordMaxWidth = array.getDimensionPixelOffset(R.styleable.CoSearchView_keywordMaxWidth, KEYWORD_MAX_WIDTH.dpInt)
		val keywordPadding = array.getDimensionPixelOffset(R.styleable.CoSearchView_keywordPadding, KEYWORD_PADDING.dpInt)
		val keywordEllipsize = array.getInt(R.styleable.CoSearchView_keywordEllipsize, KEYWORD_ELLIPSIZE)
		val keywordEnabled = array.getBoolean(R.styleable.CoSearchView_keywordEnabled, KEYWORD_ENABLED)
		val keywordAttr =
			KeywordAttr(
				keywordTextSize, keywordTextColor, keywordBackground, keywordIcon,
				keywordIconSize, keywordMaxWidth, keywordPadding, keywordEllipsize, keywordEnabled
			)
		
		array.recycle()
		return Attr(searchAttr, textAttr, navAttr, hintAttr, clearAttr, confirmAttr, keywordAttr)
	}
	
	data class Attr(
		val searchAttr: SearchAttr,
		val textAttr: TextAttr,
		val navAttr: NavAttr,
		val hintAttr: HintAttr,
		val clearAttr: ClearAttr,
		val confirmAttr: ConfirmAttr,
		val keywordAttr: KeywordAttr
	)
	
	data class SearchAttr(
		@DrawableRes val background: Int,
		@Px val horizontal: Int,
		@Px val vertical: Int,
		val changeInterval: Long
	)
	
	data class TextAttr(
		@Px val size: Int,
		@ColorInt val color: Int,
		val maxLength: Int
	)
	
	data class NavAttr(
		val icon: String,
		@Px val iconSize: Int,
		@ColorInt val color: Int,
		@Px val padding: Int,
		val typeface: String?,
		val enabled: Boolean
	)
	
	data class HintAttr(
		val icon: String,
		@Px val iconSize: Int,
		val iconEnabled: Boolean,
		val text: String,
		@Px val textSize: Int,
		@Px val padding: Int,
		@ColorInt val textColor: Int,
		val gravity: Int
	)
	
	data class ClearAttr(
		val icon: String,
		@Px val size: Int,
		@ColorInt val color: Int,
		@Px val padding: Int,
		val enabled: Boolean
	)
	
	data class ConfirmAttr(
		val icon: String?,
		val text: String?,
		@Px val size: Int,
		@ColorInt val color: Int,
		@Px val padding: Int,
		val typeface: String?,
		val enabled: Boolean
	)
	
	data class KeywordAttr(
		@Px val textSize: Int,
		@ColorInt val textColor: Int,
		@DrawableRes val background: Int,
		val icon: String,
		@Px val iconSize: Int,
		@Px val maxWidth: Int,
		@Px val padding: Int,
		val ellipsize: Int,
		val enabled: Boolean
	)
}