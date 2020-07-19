package com.michaeludjiawan.githubfinder.data.repository

import androidx.paging.PagingSource
import com.michaeludjiawan.githubfinder.data.api.ApiService
import com.michaeludjiawan.githubfinder.data.api.UsersResponse
import com.michaeludjiawan.githubfinder.data.model.User
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

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
                val message = getErrorMessage(response)
                return LoadResult.Error(Exception(message))
            }
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    private fun getErrorMessage(response: Response<UsersResponse>): String {
        return try {
            response.errorBody()?.string()?.let {
                val json = JSONObject(it)
                json.getString("message")
            }
        } catch (e: Exception) {
            response.message()
        }.orEmpty()
    }

}