package com.michaeludjiawan.githubfinder

import com.michaeludjiawan.githubfinder.data.api.ApiService
import com.michaeludjiawan.githubfinder.data.api.UsersResponse
import com.michaeludjiawan.githubfinder.data.model.User
import okhttp3.ResponseBody
import retrofit2.Response

class FakeApiService : ApiService {

    private val users = arrayListOf<User>()
    private var shouldError = false

    override suspend fun getUsers(query: String, page: Int): Response<UsersResponse> {
        if (shouldError) {
            return Response.error(500, ResponseBody.create(null, ""))
        }
        return Response.success(UsersResponse(users.count(), true, users))
    }

    fun addUser(user: User) {
        users.add(user)
    }

    fun setReturnError(shouldError: Boolean) {
        this.shouldError = shouldError
    }

    fun clear() {
        users.clear()
    }

}