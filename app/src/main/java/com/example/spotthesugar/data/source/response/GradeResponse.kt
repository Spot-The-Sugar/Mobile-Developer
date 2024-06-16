package com.example.spotthesugar.data.source.response

import com.google.gson.annotations.SerializedName

data class GradeResponse(

	@field:SerializedName("data")
	val data: GradeResult? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class GradeResult(

	@field:SerializedName("grade_desc")
	val gradeDesc: String? = null,

	@field:SerializedName("grade_id")
	val gradeId: Int? = null,

	@field:SerializedName("grade_name")
	val gradeName: String? = null
)
