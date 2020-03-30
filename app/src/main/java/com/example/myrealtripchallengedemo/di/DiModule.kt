package com.example.myrealtripchallengedemo.di

import com.example.myrealtripchallengedemo.ui.main.MainViewModel
import com.example.myrealtripchallengedemo.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelPart = module {
    viewModel { SplashViewModel(get()) }
    viewModel { MainViewModel(get()) }
}