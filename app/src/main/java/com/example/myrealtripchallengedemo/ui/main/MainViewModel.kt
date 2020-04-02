package com.example.myrealtripchallengedemo.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    private val rssRepository: RssRepository,
    private val compositeDisposable: CompositeDisposable
) : ViewModel() {

    companion object {
        const val hl = "ko"
        const val gl = "KR"
        const val ceid = "KR:ko"
    }

    private var rssItems: List<RssItem>? = null
    private val newsDetailBodyItems = ArrayList<NewsDetailBody>()

    private val _newsDetailBodyListLiveData = MutableLiveData<ArrayList<NewsDetailBody>>()
    val newsDetailBodyListLiveData: LiveData<ArrayList<NewsDetailBody>> get() = _newsDetailBodyListLiveData

    private val _startRefreshLiveEvent = MutableLiveData<Boolean>()
    val startRefreshLiveEvent: LiveData<Boolean> get() = _startRefreshLiveEvent

    fun getRssData() {
        newsDetailBodyItems.clear()
        compositeDisposable.add(rssRepository.getRssData(hl, gl, ceid)
            .doOnSubscribe { _startRefreshLiveEvent.postValue(true) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .timeout(5, TimeUnit.SECONDS)
            .doOnSuccess { getNewsBody(it.channel?.item) }
            .subscribe ({ result ->
                rssItems = result.channel?.item
                Log.e("ViewModel", "$rssItems")
            }, { error ->
                Log.e("ViewModel fail", "${error.message}")
                _startRefreshLiveEvent.postValue(false)
            }))
    }

    private fun getNewsBody(list: List<RssItem>?) {
        if(list != null) {
            for(item in list) {
                val link = item.link!!
                val title = item.title!!
                compositeDisposable.add(rssRepository.getNewsData(link)
                    .doOnSuccess {
                        parseHtmlString(title, link, it)
                        _startRefreshLiveEvent.postValue(false)
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(10, TimeUnit.SECONDS)
                    .subscribe ({ htmlStr ->
                        Log.e("ViewModel success in news body", htmlStr)
                    }, { error ->
                        Log.e("ViewModel fail in news body", "${error.message}")
                        _startRefreshLiveEvent.postValue(false)
                    }))
            }
        } else Log.e("ViewModel", "News List is Empty!!")
    }

    private fun parseHtmlString(title: String?, link: String?, htmlStr: String?) {
        val temp = ArrayList<String>()
        val strMap = mutableMapOf<String, Int>()
        val doc = Jsoup.parse(htmlStr)
        val imgUrl = doc.select("meta[property=og:image]").attr("content")
        val description = doc.select("meta[property=og:description]").attr("content")

        val arr = description.split(" ", "“", "”", "‘", "’",
            ",", "[", "]", "...", "=", ".", "(", ")", ":", "\"", "\'", "\n") as MutableList

        for(str in arr) {
            if(str.length >= 2) {
                var tempStr = ""
                for(strElement in str) {
                    if(strElement != ' ') {
                        tempStr += strElement
                    }
                }
                Log.e("wordasdf", tempStr)
                temp.add(tempStr)
                strMap[tempStr] = 0
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
        val newsDetailBody = NewsDetailBody(title, link, imgUrl, description, keyWord)
        Log.e("newsDetail", "$newsDetailBody")
        newsDetailBodyItems.add(newsDetailBody)
        _newsDetailBodyListLiveData.postValue(newsDetailBodyItems)
    }

    fun itemListSize(): Int = this.newsDetailBodyItems.size

    fun getNewsItem(position: Int): NewsDetailBody = this.newsDetailBodyItems[position]

    fun destroyDisposable() {
        compositeDisposable.clear()
    }
}