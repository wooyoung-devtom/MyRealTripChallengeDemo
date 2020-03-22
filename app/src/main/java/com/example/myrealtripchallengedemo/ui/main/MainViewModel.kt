package com.example.myrealtripchallengedemo.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrealtripchallengedemo.data.dto.NewsBody
import com.example.myrealtripchallengedemo.data.dto.RssFeed
import com.example.myrealtripchallengedemo.data.dto.RssItem
import com.example.myrealtripchallengedemo.data.repository.RssRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val rssRepository: RssRepository
) : ViewModel() {

    companion object {
        const val hl = "ko"
        const val gl = "KR"
        const val ceid = "KR:ko"
    }

    var rssItems: List<RssItem>? = null
    var newsBodyItems: ArrayList<NewsBody>? = null

    private val compositeDisposable = CompositeDisposable()

    private val _rssLiveData = MutableLiveData<RssFeed>()
    val rssLiveData: LiveData<RssFeed> get() = _rssLiveData

    private val _newsTextLiveData = MutableLiveData<ArrayList<NewsBody>>()
    val newsTextLiveData: LiveData<ArrayList<NewsBody>> get() = _newsTextLiveData

    fun getRssData() {
        compositeDisposable.add(rssRepository.getRssData(hl, gl, ceid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .timeout(5, TimeUnit.SECONDS)
            .doOnSuccess { getNewsText(it.channel?.item) }
            .subscribe ({ result ->
                _rssLiveData.postValue(result)
                rssItems = result.channel?.item
                Log.e("ViewModel", "$result")
            }, { error ->
                Log.e("ViewModel fail", "${error.message}")
            })
        )
    }

    fun getNewsText(list: List<RssItem>?) {
        GlobalScope.launch(Dispatchers.IO) {
            for(item in list!!) {
                val newsBody = rssRepository.getNewsData(item.link!!)
                newsBodyItems?.add(newsBody)
            }

            Log.e("getNewsText", "$newsBodyItems")
        }
    }

    fun itemListSize(): Int = this.rssItems?.size ?: 0

    fun getRssItem(position: Int): RssItem? = this.rssItems?.get(position)

    fun getNewsItem(position: Int): NewsBody? = this.newsBodyItems?.get(position)

    fun destroyDisposable() {
        compositeDisposable.clear()
    }
}