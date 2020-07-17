package com.michaeludjiawan.githubfinder.data.repository

import androidx.paging.PagingSource
import com.michaeludjiawan.githubfinder.data.api.ApiService
import com.michaeludjiawan.githubfinder.data.model.User

class UserPagingSource(
    private val apiService: ApiService,
    private val query: String
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val position = params.key ?: 1
        return try {
            val response = apiService.getUsers(query, position)
            if (response.isSuccessful) {
                val users = response.body()?.items.orEmpty()
                LoadResult.Page(
                    data = users,
                    prevKey = if (position == 1) null else position - 1,
                    nextKey = if (users.isEmpty()) null else position + 1
                )
            } else {
                return LoadResult.Error(Exception())
            }
        } catch (throwable: Throwable) {
            return LoadResult.Error(throwable)
        }
    }

}