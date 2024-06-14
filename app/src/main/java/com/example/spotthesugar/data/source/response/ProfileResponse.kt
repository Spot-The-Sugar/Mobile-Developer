package com.example.spotthesugar.data.source.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("data")
	val profileResult: ProfileResult? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class ProfileResult(

	@field:SerializedName("user_email")
	val userEmail: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("user_name")
	val userName: String? = null,

	@field:SerializedName("user_weight")
	val userWeight: Any? = null,

	@field:SerializedName("user_age")
	val userAge: Any? = null,

	@field:SerializedName("user_height")
	val userHeight: Any? = null,

	@field:SerializedName("sugar_limit")
	val sugarLimit: Any? = null
)
