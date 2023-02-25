package com.cooder.cooder.library.util

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/12 17:03
 *
 * 介绍：域名工具类
 */
object DomainUtil {
	
	/**
	 * 获取真实域名
	 */
	fun getRealDomainUrl(domain: String, endIsSlash: Boolean = false): String {
		checkDomainUrlLegal(domain)
		val slashCount = domain.split('/').size - 1
		return if (slashCount == 2) {      // 这种情况要判断域名是否正确，因为可能出现 https://的这种情况
			if (domain.endsWith('/')) throw IllegalStateException("The domain $domain is not legal!")
			if (endIsSlash) "$domain/" else domain
		} else if (slashCount > 2) {
			val length = getRealDomainLength(domain)
			domain.substring(0, if (endIsSlash) length else length - 1)
		} else {
			throw IllegalStateException("The domain $domain is not legal!")
		}
	}
	
	/**
	 * 检查域名前缀是否是http:// 或者 https://
	 */
	private fun checkDomainUrlLegal(domain: String) {
		if (!(domain.startsWith("http://") || domain.startsWith("https://"))) {
			throw IllegalStateException("The domain $domain is not legal!")
		}
	}
	
	/**
	 * 获取域名长度
	 */
	private fun getRealDomainLength(domain: String): Int {
		var i = 1
		var n = 3
		for (s in domain) {
			if (s == '/') n--
			if (n == 0) return i
			i++
		}
		return domain.length
	}
}