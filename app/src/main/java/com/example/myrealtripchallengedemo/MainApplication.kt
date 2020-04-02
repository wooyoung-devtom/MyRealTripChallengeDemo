package com.example.myrealtripchallengedemo

import android.app.Application
import com.example.myrealtripchallengedemo.di.apiModule
import com.example.myrealtripchallengedemo.di.compositeDisposable
import com.example.myrealtripchallengedemo.di.viewModelPart
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    private val modules = listOf(
        viewModelPart,
        compositeDisposable,
        apiModule)

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(modules)
        }
    }
}