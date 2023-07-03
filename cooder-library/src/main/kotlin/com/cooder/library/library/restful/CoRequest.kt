package com.cooder.library.library.restful

import androidx.annotation.IntDef
import com.cooder.library.library.restful.annotation.CacheStrategy
import com.cooder.library.library.util.DomainUtil
import java.io.UnsupportedEncodingException
import java.lang.reflect.Type
import java.net.URLEncoder

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/11 15:07
 *
 * 介绍：CoRequest
 */
open class CoRequest {
	
	/**
	 * http方法
	 */
	@METHOD
	var httpMethod: Int = METHOD.NONE
	
	/**
	 * 请求头
	 */
	var headers: MutableMap<String, String>? = null
	
	/**
	 * 参数
	 */
	var parameters: MutableMap<String, String>? = null
	
	/**
	 * 域名
	 */
	var domainUrl: String? = null
	
	/**
	 * 相对路径
	 */
	var relativeUrl: String? = null
	
	/**
	 * 返回类型
	 */
	var returnType: Type? = null
	
	/**
	 * POST请求时，是否为表单提交，默认: true
	 */
	var formPost = true
	
	/**
	 * 缓存策略类型
	 */
	var cacheStrategy: CacheStrategy.Type = CacheStrategy.Type.NET_ONLY
	
	/**
	 * 缓存策略键
	 */
	private var cacheStrategyKey: String = ""
	
	@IntDef(METHOD.NONE, METHOD.GET, METHOD.POST)
	annotation class METHOD {
		companion object {
			const val NONE = -1
			const val GET = 0
			const val POST = 1
		}
	}
	
	/**
	 * 获取完整路径
	 */
	fun getCompleteUrl(): String {
		check(domainUrl != null) { "domainUrl == null" }
		check(relativeUrl != null) { "relativeUrl == null" }
		// 表示从域名往后写
		if (relativeUrl!!.startsWith("//")) {
			val realDomain = DomainUtil.getRealDomainUrl(domainUrl!!)
			return realDomain + relativeUrl!!.removePrefix("//")
		}
		if (relativeUrl!!.startsWith("/")) {
			if (domainUrl!!.endsWith("/")) domainUrl = domainUrl!!.removeSuffix("/")
			return domainUrl + relativeUrl
		}
		throw IllegalStateException("There is a problem with the format of \"$relativeUrl\"")
	}
	
	fun addHeader(name: String, value: String) {
		if (headers == null) headers = mutableMapOf()
		headers!![name] = value
	}
	
	/**
	 * 获取缓存Key
	 */
	fun getCacheKey(): String {
		if (cacheStrategyKey.isNotEmpty()) {
			return cacheStrategyKey
		}
		val url = getCompleteUrl()
		val cacheKey = StringBuilder()
		// net::http://xxx.xxx.xxx.xxx/xxx/xxx?xxx=xxx&xxx=xxx
		cacheKey.append("net::").append(url)
		if (parameters!!.isNotEmpty()) {
			if (url.contains('?')) {
				if (!(url.endsWith('&') || url.endsWith('?'))) {
					cacheKey.append('&')
				}
			} else {
				cacheKey.append('?')
			}
			parameters!!.forEach { key: String, value: String ->
				try {
					val encodeValue = URLEncoder.encode(value, "UTF-8")
					cacheKey.append(key).append('=').append(encodeValue).append('&')
				} catch (e: UnsupportedEncodingException) {
					// Ignore
				}
			}
			cacheKey.delete(cacheKey.length - 1, cacheKey.length)
		}
		cacheStrategyKey = cacheKey.toString()
		return cacheStrategyKey
	}
	
	override fun toString(): String {
		return "CoRequest(httpMethod=$httpMethod, headers=$headers, parameters=$parameters, domainUrl=$domainUrl, relativeUrl=$relativeUrl, returnType=$returnType, formPost=$formPost, cacheStrategy=$cacheStrategy, cacheStrategyKey='$cacheStrategyKey')"
	}
}