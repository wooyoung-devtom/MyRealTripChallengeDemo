package com.example.myrealtripchallengedemo.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrealtripchallengedemo.data.RssRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

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
            .subscribe ({ result ->
                _rssLiveData.postValue(result)
            }, { error ->
                _rssLiveData.postValue(error.message)
            })
        )
    }
}