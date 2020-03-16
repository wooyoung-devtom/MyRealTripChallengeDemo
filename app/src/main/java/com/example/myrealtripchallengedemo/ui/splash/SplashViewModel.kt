package com.example.myrealtripchallengedemo.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.android.BuildConfig

class SplashViewModel : ViewModel() {

    private val _versionTextLiveData: MutableLiveData<String> = MutableLiveData()
    val versionTextLiveData: LiveData<String> get() = _versionTextLiveData

    private val _startMainFragmentEvent: MutableLiveData<Any> = MutableLiveData()
    val startMainFragmentEvent: LiveData<Any> get() = _startMainFragmentEvent

    fun getApplicationVersion() {
        _versionTextLiveData.postValue("v ${BuildConfig.VERSION_CODE}")
    }


}