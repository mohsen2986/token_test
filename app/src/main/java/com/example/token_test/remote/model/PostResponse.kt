package com.example.token_test.remote.model

import com.google.gson.annotations.SerializedName

data class PostResponse(
        @SerializedName("data")
        val datas:List<Post>
)