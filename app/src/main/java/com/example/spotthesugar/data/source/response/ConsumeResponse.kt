package com.example.spotthesugar.data.source.response

import com.google.gson.annotations.SerializedName

data class ConsumeResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
