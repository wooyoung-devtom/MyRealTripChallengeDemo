package com.example.myrealtripchallengedemo.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrealtripchallengedemo.data.repository.RssRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val rssRepository: RssRepository
) : ViewModel() {

    companion object {
        const val hl = "ko"
        const val gl = "KR"
        const val ceid = "KR:ko"
    }

    private val compositeDisposable = CompositeDisposable()

    private val _rssLiveData = MutableLiveData<String>()
    val rssLiveData: LiveData<String> get() = _rssLiveData

    fun getRssData() {
        compositeDisposable.add(rssRepository.getRssData(hl, gl, ceid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .timeout(5, TimeUnit.SECONDS)
            .subscribe ({ result ->
                _rssLiveData.postValue(result)
                Log.e("ViewModel", "$result")
            }, { error ->
                _rssLiveData.postValue(error.message)
            })
        )
    }

    fun parseRssData(rssData: String) {

    }

    fun destroyDisposable() {
        compositeDisposable.clear()
    }
}