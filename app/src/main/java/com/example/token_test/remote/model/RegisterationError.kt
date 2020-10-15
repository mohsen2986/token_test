package com.example.token_test.remote.model

import com.google.gson.annotations.SerializedName

data class RegisterationError(
        @SerializedName("email")
        val email: List<String> ,
        @SerializedName("name")
        val name: List<String> ,
        @SerializedName("password")
        val password: List<String>
)