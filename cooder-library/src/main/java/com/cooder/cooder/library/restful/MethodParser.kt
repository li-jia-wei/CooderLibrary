package com.cooder.cooder.library.restful

import com.cooder.cooder.library.restful.annotation.*
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * 项目：CooderLibrary
 *
 * 作者：李佳伟
 *
 * 创建：2022/11/11 15:45
 *
 * 介绍：方法解析器
 */
class MethodParser(
	private val baseUrl: String, method: Method
) {
	
	/**
	 * 域名
	 */
	private var domainUrl: String? = null
	
	/**
	 * 原始相对路径，需要使用它来解析替换@Path等，放置第二次复用不了
	 */
	private var rawRelativeUrl: String? = null
	
	/**
	 * 相对路径真实Url
	 */
	private var relativeUrl: String? = null
	
	/**
	 * 返回类型
	 */
	private var returnType: Type? = null
	
	/**
	 * 请求方式
	 */
	@CooderRequest.METHOD
	private var httpMethod: Int = CooderRequest.METHOD.NONE
	
	/**
	 * 当请求方式为POST时，是否是表单提交
	 */
	private var formPost = true
	
	private var headers = mutableMapOf<String, String>()
	
	private var parameters = mutableMapOf<String, String>()
	
	init {
		parseMethodAnnotations(method)
		parseMethodGenericReturnType(method)
	}
	
	companion object {
		fun parse(baseUrl: String, method: Method): MethodParser {
			return MethodParser(baseUrl, method)
		}
	}
	
	/**
	 * 创建Request对象
	 */
	fun newRequest(method: Method, args: Array<Any>?): CooderRequest {
		parseMethodParameters(method, args)
		val request = CooderRequest()
		request.domainUrl = domainUrl
		request.relativeUrl = relativeUrl
		request.parameters = parameters
		request.returnType = returnType
		request.headers = headers
		request.httpMethod = httpMethod
		request.formPost = formPost
		return request
	}
	
	/**
	 * 解析方法注解，如: Headers, BaseUrl, GET, POST
	 */
	private fun parseMethodAnnotations(method: Method) {
		val annotations = method.annotations
		annotations.forEach {
			when (it) {
				is DomainUrl -> {
					requireUrlNotNullOrBlank(it.value, DomainUrl::class, method.name)
					this.domainUrl = it.value
				}
				is Headers -> {
					it.value.forEach {
						val colonPos = it.indexOf(":")
						check(colonPos != 0 && colonPos != it.length - 1 && colonPos != -1) {
							"The format of the @Headers value must be [name:key], but found ${method.name}"
						}
						val name = it.substring(0, colonPos).trim()
						val value = it.substring(colonPos + 1).trim()
						headers[name] = value
					}
				}
				is GET -> {
					checkRepeatedSettingHttpMethod(method.name)
					requireUrlNotNullOrBlank(it.url, GET::class, method.name)
					rawRelativeUrl = it.url
					httpMethod = CooderRequest.METHOD.GET
				}
				is POST -> {
					checkRepeatedSettingHttpMethod(method.name)
					requireUrlNotNullOrBlank(it.url, POST::class, method.name)
					rawRelativeUrl = it.url
					formPost = it.formPost
					httpMethod = CooderRequest.METHOD.POST
				}
				else -> {
					throw IllegalStateException("Cannot handle method annotation : ${it.javaClass}")
				}
			}
		}
		requireMustSettingAnnotations(method.name)
		if (domainUrl == null) domainUrl = baseUrl
	}
	
	/**
	 * 解析方法参数，如: Filed, Replace
	 */
	private fun parseMethodParameters(method: Method, args: Array<Any>?) {
		// 将原始相对路径赋值上去，放置之前被相同的Method复用之后再次Path替换不了
		relativeUrl = rawRelativeUrl
		if (args == null || args.isEmpty()) return
		val parameterAnnotations = method.parameterAnnotations
		val equals = parameterAnnotations.size == args.size
		require(equals) {
			"The arguments annotations count ${parameterAnnotations.size} don't match expect count ${args.size}, but found ${method.name}"
		}
		parameters.clear()
		args.indices.forEach {
			val annotations = parameterAnnotations[it]
			require(annotations.size == 1) {
				"The parameter has one and only one annotation, index = $it"
			}
			val value = args[it]
			require(isPrimitive(value)) {
				"Currently, only 8 basic data types and String types are supported."
			}
			when (val annotation = annotations[0]) {
				is Filed -> {
					val key = annotation.name
					parameters[key] = value.toString()
				}
				is Path -> {
					val replaceName = annotation.replacedName
					val replaceValue = value.toString()
					if (replaceName.isNotBlank()) {
						replaceValue(replaceName, replaceValue)
					}
				}
				else -> {
					throw IllegalStateException("Cannot handle method annotation : ${it.javaClass}")
				}
			}
		}
	}
	
	/**
	 * 替换所有注解上的信息
	 */
	private fun replaceValue(replaceName: String, replaceValue: String) {
		// 替换相对路径中的Path，在此之前是替换域名，认为domainUrl中不应该存在被替换的地方
		if (relativeUrl!!.indexOf("{$replaceName}") != -1) {
			relativeUrl = relativeUrl!!.replace("{$replaceName}", replaceValue)
		}
	}
	
	/**
	 * 查看是否是基本数据类型
	 */
	private fun isPrimitive(value: Any): Boolean {
		if (value.javaClass == String::class.java) return true
		try {
			val field = value.javaClass.getField("TYPE")
			val clazz = field[null] as Class<*>
			return clazz.isPrimitive
		} catch (e: IllegalAccessException) {
			e.printStackTrace()
		} catch (e: IllegalArgumentException) {
			e.printStackTrace()
		} catch (e: NoSuchFieldException) {
			e.printStackTrace()
		}
		return false
	}
	
	/**
	 * 解析方法泛型返回类型
	 */
	private fun parseMethodGenericReturnType(method: Method) {
		check(method.returnType == CooderCall::class.java) {
			"The method ${method.name} return type must be type of CooderCall::class.java."
		}
		val genericReturnType = method.genericReturnType
		if (genericReturnType is ParameterizedType) {
			val actualTypeArguments = genericReturnType.actualTypeArguments
			require(actualTypeArguments.size == 1) {
				"The method ${method.name} can only has one generic return actual type."
			}
			returnType = actualTypeArguments[0]
		} else {
			throw IllegalStateException("The method ${method.name} must has one generic return actual type. ")
		}
	}
	
	/**
	 * 检查有没有重复指定GET或者POST
	 */
	private fun checkRepeatedSettingHttpMethod(methodName: String) {
		check(httpMethod == CooderRequest.METHOD.NONE) {
			"The method $methodName already set up httpMethod, cannot be repeated settings it."
		}
	}
	
	/**
	 * 检查@DomainUrl, @GET, @POST注解设置url时是否设置为空格的url
	 */
	private fun requireUrlNotNullOrBlank(url: String, httpMethod: KClass<out Annotation>, methodName: String) {
		require(url.isNotBlank()) {
			"The @${httpMethod.simpleName} value cannot be null or only have spaces, but found $methodName"
		}
	}
	
	/**
	 * 检查设置必须要设置的注解有没有设置
	 */
	private fun requireMustSettingAnnotations(methodName: String) {
		require(httpMethod == CooderRequest.METHOD.GET || httpMethod == CooderRequest.METHOD.POST) {
			"The method $methodName must have a request mode, such as @GET or @POST."
		}
	}
}