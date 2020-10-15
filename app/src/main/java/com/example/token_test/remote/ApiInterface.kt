package com.example.token_test.remote

import com.example.token_test.remote.model.AccessToken
import com.example.token_test.remote.model.ErrorResponse
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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

}