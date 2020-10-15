package com.example.token_test.remote.model

import com.google.gson.annotations.SerializedName

data class AccessToken(
    @SerializedName("token_type")
    val tokenType: String ,
    @SerializedName("expired_in")
    val  expiredIn: Int,
    @SerializedName("refresh_token")
    val refreshToken: String
)