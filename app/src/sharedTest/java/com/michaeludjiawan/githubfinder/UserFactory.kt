package com.michaeludjiawan.githubfinder

import com.michaeludjiawan.githubfinder.data.model.User
import java.util.concurrent.atomic.AtomicInteger

class UserFactory {

    private val counter = AtomicInteger(0)
    fun createUser(userName: String): User {
        val id = counter.incrementAndGet()
        return User(
            id = id,
            loginName = "name_$id",
            avatarUrl = ""
        )
    }

}