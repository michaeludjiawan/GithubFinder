package com.michaeludjiawan.githubfinder.data.api

import com.google.gson.annotations.SerializedName
import com.michaeludjiawan.githubfinder.data.model.User

data class UsersResponse(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val isIncomplete: Boolean,
    @SerializedName("items") val items: List<User>
)