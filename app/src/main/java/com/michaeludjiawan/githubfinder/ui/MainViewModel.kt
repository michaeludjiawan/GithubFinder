package com.michaeludjiawan.githubfinder.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.paging.PagingData
import com.michaeludjiawan.githubfinder.data.model.User
import com.michaeludjiawan.githubfinder.data.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class MainViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)
    private val mutableQuery = MutableLiveData<String>()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val users = flowOf(
        clearListCh.consumeAsFlow().map { PagingData.empty<User>() },
        mutableQuery
            .asFlow()
            .flatMapLatest { userRepository.getUsers(it, 1) }
    ).flattenMerge()

    fun getUsers(query: String) {
        clearListCh.offer(Unit)
        mutableQuery.value = query
    }

}