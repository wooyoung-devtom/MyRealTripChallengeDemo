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
import kotlinx.coroutines.*
import org.jsoup.Jsoup
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
    private val newsBodyItems = ArrayList<NewsBody>()

    private val compositeDisposable = CompositeDisposable()

    private val _rssLiveData = MutableLiveData<RssFeed>()
    val rssLiveData: LiveData<RssFeed> get() = _rssLiveData

    private val _newsTextLiveData = MutableLiveData<ArrayList<NewsBody>>()
    val newsTextLiveData: LiveData<ArrayList<NewsBody>> get() = _newsTextLiveData

    private val _stopRefreshLiveEvent = MutableLiveData<Boolean>()
    val stopRefreshLiveEvent: LiveData<Boolean> get() = _stopRefreshLiveEvent

    fun getRssData() {
        compositeDisposable.add(rssRepository.getRssData(hl, gl, ceid)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .timeout(5, TimeUnit.SECONDS)
            .doOnSuccess { getNewsBody(it.channel?.item) }
            .subscribe ({ result ->
                rssItems = result.channel?.item
                Log.e("ViewModel", "$rssItems")
            }, { error ->
                Log.e("ViewModel fail", "${error.message}")
            }))
    }

    private fun getNewsBody(list: List<RssItem>?) {
        if(list != null) {
            for(item in list) {
                val link = item.link!!
                val title = item.title!!
                compositeDisposable.add(rssRepository.getNewsData(link)
                    .doOnSubscribe { _stopRefreshLiveEvent.postValue(true) }
                    .doOnSuccess { _stopRefreshLiveEvent.postValue(false) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(5, TimeUnit.SECONDS)
                    .subscribe ({ result ->
                        val doc = Jsoup.parse(result)
                        val imgUrl = doc.select("meta[property=og:image]").attr("content")
                        val description = doc.select("meta[property=og:description]").attr("content")
                        val arr = description.split(" ", "“", "”", "‘", "’", ",", "[", "]", "...", "=", ".",
                        "(", ")") as MutableList
                        for(str in arr) Log.e("ViewModel", "$str , ${str.length}")
//                        Log.e("ViewModel new Description", "$arr")
                        val newsBody = NewsBody(title, link, imgUrl, description, arr, null)

                        newsBodyItems.add(newsBody)
                        _newsTextLiveData.postValue(newsBodyItems)
                    }, { error ->
                        Log.e("ViewModel fail in news body", "${error.message}")
                    }))
            }
            Log.e("ViewModel", "$newsBodyItems")
        } else Log.e("ViewModel", "News List is Empty!!")
    }

    private fun getKeyword(newsBodyItems: ArrayList<NewsBody>) {
        for(newsBodyItem in newsBodyItems) {

        }
    }

    fun itemListSize(): Int = this.newsBodyItems.size

    fun getRssItem(position: Int): RssItem? = this.rssItems?.get(position)

    fun getNewsItem(position: Int): NewsBody? = this.newsBodyItems[position]

    fun destroyDisposable() {
        compositeDisposable.clear()
    }
}