package com.example.spotthesugar.data.source.response

import com.google.gson.annotations.SerializedName

data class TrackResponse(

	@field:SerializedName("data")
	val data: TrackResult? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class TrackResult(

	@field:SerializedName("user_name")
	val userName: String? = null,

	@field:SerializedName("consume_sugar")
	val consumeSugar: Int? = null,

	@field:SerializedName("consume_date")
	val consumeDate: String? = null,

	@field:SerializedName("sugar_limit")
	val sugarLimit: Int? = null
)
