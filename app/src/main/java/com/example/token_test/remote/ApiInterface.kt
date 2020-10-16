package com.example.token_test.remote

import com.example.token_test.remote.model.AccessToken
import com.example.token_test.remote.model.ErrorResponse
import com.example.token_test.remote.model.PostResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface{

    @POST("register")
    @FormUrlEncoded
    suspend fun register(
        @Field("name")
        name: String ,
        @Field("email")
        email: String ,
        @Field("password")
        password: String ,
        @Field("password_confirmation")
        passwordConfirm: String
    ): NetworkResponse<AccessToken, ErrorResponse>

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
            @Field("username")
            username: String ,
            @Field("password")
            password: String
    ): NetworkResponse<AccessToken , ErrorResponse>

    @POST("refresh")
    @FormUrlEncoded
    suspend fun refresh(
            @Field("refresh_token")
            refreshToken: String
    ): NetworkResponse<AccessToken , ErrorResponse>

    @GET("posts")
    suspend fun getPost(
    ): NetworkResponse<PostResponse, ErrorResponse>
}