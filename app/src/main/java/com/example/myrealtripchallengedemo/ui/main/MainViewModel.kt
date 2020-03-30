package com.example.myrealtripchallengedemo.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrealtripchallengedemo.data.dto.NewsBody
import com.example.myrealtripchallengedemo.data.dto.NewsDetailBody
import com.example.myrealtripchallengedemo.data.dto.NewsDetailBody.KeyWord
import com.example.myrealtripchallengedemo.data.dto.RssItem
import com.example.myrealtripchallengedemo.data.repository.RssRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
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

    private var rssItems: List<RssItem>? = null
    private val newsDetailBodyItems = ArrayList<NewsDetailBody>()
    private val compositeDisposable = CompositeDisposable()

    private val _newsTextLiveData = MutableLiveData<NewsBody>()
    val newsTextLiveData: LiveData<NewsBody> get() = _newsTextLiveData

    private val _newsDetailBodyListLiveData = MutableLiveData<ArrayList<NewsDetailBody>>()
    val newsDetailBodyListLiveData: LiveData<ArrayList<NewsDetailBody>> get() = _newsDetailBodyListLiveData

    private val _startRefreshLiveEvent = MutableLiveData<Boolean>()
    val startRefreshLiveEvent: LiveData<Boolean> get() = _startRefreshLiveEvent

    fun getRssData() {
        newsDetailBodyItems.clear()
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
                    .doOnSubscribe { _startRefreshLiveEvent.postValue(true) }
                    .doOnSuccess { _startRefreshLiveEvent.postValue(false) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(10, TimeUnit.SECONDS)
                    .subscribe ({ result ->
                        val newsBody = NewsBody(title, link, result)
                        _newsTextLiveData.postValue(newsBody)
                        Log.e("ViewModel success in news body", "$newsBody")
                    }, { error ->
                        Log.e("ViewModel fail in news body", "${error.message}")
                    }))
            }
        } else Log.e("ViewModel", "News List is Empty!!")
    }

    fun parseHtmlString(newsBody: NewsBody) {
        val temp = ArrayList<String>()
        val strMap = mutableMapOf<String, Int>()
        val doc = Jsoup.parse(newsBody.html)
        val imgUrl = doc.select("meta[property=og:image]").attr("content")
        val description = doc.select("meta[property=og:description]").attr("content")

        val arr = description.split(" ", "“", "”", "‘", "’", ",",
            "[", "]", "...", "=", ".", "(", ")", ":", "\"", "\'") as MutableList

        for(str in arr) {
            if(str.length >= 2) {
                temp.add(str)
                strMap[str] = 0
            }
        }
        for(str in temp) {
            val count = strMap[str]?.plus(1)
            strMap[str] = count!!
        }
        val keySortedMap = strMap.toSortedMap()
        val listMap = keySortedMap.toList()
            .sortedWith(compareByDescending { it.second })
        Log.e("Parse HTML", "$listMap")

        val keyWord = KeyWord(listMap[0].first, listMap[1].first, listMap[2].first)
        val newsDetailBody = NewsDetailBody(imgUrl, description, newsBody, keyWord)
        newsDetailBodyItems.add(newsDetailBody)
        _newsDetailBodyListLiveData.postValue(newsDetailBodyItems)
    }

    fun itemListSize(): Int = this.newsDetailBodyItems.size

    fun getNewsItem(position: Int): NewsDetailBody = this.newsDetailBodyItems[position]

    fun destroyDisposable() {
        compositeDisposable.clear()
    }
}