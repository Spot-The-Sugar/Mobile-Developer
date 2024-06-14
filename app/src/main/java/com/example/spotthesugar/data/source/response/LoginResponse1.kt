package com.example.spotthesugar.data.source.response

data class LoginResponse1(
	val data: Data? = null,
	val message: String? = null,
	val status: String? = null
)

data class Data(
	val token: String? = null
)

