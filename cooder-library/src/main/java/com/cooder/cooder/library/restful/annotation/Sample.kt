package com.cooder.cooder.library.restful.annotation

import com.cooder.cooder.library.restful.CoCall
import org.json.JSONObject

/**
 * 示例
 */
private interface Sample {
	
	@DomainUrl("http://api.cooder.com/")
	@Headers("connection:keep-alive", "auth-token:token")
	@GET(url = "/city/{city}")
	fun requestByGet(@Path("city") city: String, @Filed("name") name: String): CoCall<JSONObject>
	
	@DomainUrl("http://api.cooder.com/")
	@POST("/city/shanghai", false)
	fun requestByPost(@Filed("json") json: String): CoCall<JSONObject>
}