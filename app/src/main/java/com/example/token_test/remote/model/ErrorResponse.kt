package com.example.token_test.remote.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message")
    val message: String ,
    @SerializedName("errors")
    val errors: RegisterationError
)