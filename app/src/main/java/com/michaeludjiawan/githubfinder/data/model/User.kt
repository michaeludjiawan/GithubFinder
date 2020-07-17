package com.michaeludjiawan.githubfinder.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("login") val loginName: String,
    @SerializedName("avatar_url") val avatarUrl: String
)