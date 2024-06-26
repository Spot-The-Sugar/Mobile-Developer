package com.example.spotthesugar.data.source.service

import com.example.spotthesugar.data.source.response.EditProfileResponse
import com.example.spotthesugar.data.source.response.GradeResponse
import com.example.spotthesugar.data.source.response.HistoryDetailResponse
import com.example.spotthesugar.data.source.response.HistoryResponse
import com.example.spotthesugar.data.source.response.LoginResponse
import com.example.spotthesugar.data.source.response.LoginResponse1
import com.example.spotthesugar.data.source.response.ProfileResponse
import com.example.spotthesugar.data.source.response.RegisterResponse
import com.example.spotthesugar.data.source.response.RegisterResponse1
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("pass") password:String,
    ): RegisterResponse1

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("pass") password: String
    ): LoginResponse1


    @GET("profile")
    suspend fun profile(
        @Header("Authorization") bearerToken: String
    ): ProfileResponse

    @FormUrlEncoded
    @PUT("profile/edit")
    suspend fun updateProfile(
        @Header("Authorization") bearerToken: String,
        @Field("name") name: String,
        @Field("age") age: Int,
        @Field("height") height: Double,
        @Field("weight") weight: Double,
        @Field("limit") limit: Int,
    ): EditProfileResponse

    @GET("history")
    suspend fun getHistory(
        @Header("Authorization") bearerToken: String
    ): HistoryResponse

    @GET("history/{id}")
    suspend fun getDetailHistory(
        @Header("Authorization") bearerToken: String,
        @Path("id") id:Int
    ): HistoryDetailResponse

    @GET("grade/{id}")
    suspend fun getGrade(
        @Header("Authorization") bearerToken: String,
        @Path("id") id:Int
    ): GradeResponse


}