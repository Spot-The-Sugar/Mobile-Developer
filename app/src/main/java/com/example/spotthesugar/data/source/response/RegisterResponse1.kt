package com.example.spotthesugar.data.source.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse1(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
