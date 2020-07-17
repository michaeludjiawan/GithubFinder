package com.michaeludjiawan.githubfinder.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.michaeludjiawan.githubfinder.data.api.ApiService
import com.michaeludjiawan.githubfinder.data.model.User
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    private val apiService: ApiService
) : UserRepository {

    override fun getUsers(q: String, page: Int): Flow<PagingData<User>> =
        Pager(
            config = PagingConfig(pageSize = 20),
            initialKey = page,
            pagingSourceFactory = { UserPagingSource(apiService, q) }
        ).flow

}