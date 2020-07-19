package com.michaeludjiawan.githubfinder.di

import com.michaeludjiawan.githubfinder.data.repository.UserRepository
import com.michaeludjiawan.githubfinder.data.repository.UserRepositoryImpl
import com.michaeludjiawan.githubfinder.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {
    single<UserRepository> { UserRepositoryImpl(get()) }

    viewModel { MainViewModel(get()) }
}