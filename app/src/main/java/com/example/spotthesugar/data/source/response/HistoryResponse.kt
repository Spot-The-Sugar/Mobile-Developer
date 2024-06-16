package com.example.spotthesugar.data.source.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DataItem(

	@field:SerializedName("serving_sugar")
	val servingSugar: Double? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("product_name")
	val productName: String? = null,

	@field:SerializedName("total_sugar")
	val totalSugar: Double? = null,

	@field:SerializedName("serving_pack")
	val servingPack: Double? = null,

	@field:SerializedName("product_type")
	val productType: String? = null,

	@field:SerializedName("product_grade")
	val productGrade: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("product_id")
	val productId: Int? = null,

	@field:SerializedName("serving_size")
	val servingSize: Double? = null,

	@field:SerializedName("scan_consume")
	val scanConsume: Int? = null,

	@field:SerializedName("scan_id")
	val scanId: Int? = null,

	@field:SerializedName("scan_date")
	val scanDate: String? = null,

	@field:SerializedName("product_category")
	val productCategory: String? = null
)
