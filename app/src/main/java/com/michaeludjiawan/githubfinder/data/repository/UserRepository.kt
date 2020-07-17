package com.michaeludjiawan.githubfinder.data.repository

import androidx.paging.PagingData
import com.michaeludjiawan.githubfinder.data.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(q: String, page: Int): Flow<PagingData<User>>
}