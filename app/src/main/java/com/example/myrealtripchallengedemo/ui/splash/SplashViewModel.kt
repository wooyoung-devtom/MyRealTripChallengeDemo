package com.example.myrealtripchallengedemo.ui.splash

import android.app.Application
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.android.BuildConfig

class SplashViewModel(
    private val context: Context
) : ViewModel() {

    private val _versionTextLiveData: MutableLiveData<String> = MutableLiveData()
    val versionTextLiveData: LiveData<String> get() = _versionTextLiveData

    private val _startMainFragmentEvent: MutableLiveData<Any> = MutableLiveData()
    val startMainFragmentEvent: LiveData<Any> get() = _startMainFragmentEvent

    fun getApplicationVersion() {
        val info = context.applicationContext.packageManager.getPackageInfo(context.packageName, 0)
        val version = info.versionName
        _versionTextLiveData.postValue("v $version")
    }

    fun startMainFragment() {
        _startMainFragmentEvent
    }
}