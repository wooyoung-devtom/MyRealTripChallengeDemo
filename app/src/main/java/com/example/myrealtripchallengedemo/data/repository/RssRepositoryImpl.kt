package com.example.myrealtripchallengedemo.data.repository

import android.util.Log
import com.example.myrealtripchallengedemo.data.api.RssService
import com.example.myrealtripchallengedemo.data.dto.NewsBody
import com.example.myrealtripchallengedemo.data.dto.RssFeed
import io.reactivex.Single
import org.jsoup.Jsoup

class RssRepositoryImpl (
    private val rssService: RssService
): RssRepository {

    override fun getRssData(hl: String, gl: String, ceid: String): Single<RssFeed> {
        return rssService.getRssData(hl, gl, ceid)
    }

    override fun getNewsData(url: String): NewsBody {
        val document = Jsoup.connect(url).get()
        val imgUrl = document.select("meta[property=og:image]").first().attr("content")
        val description = document.select("meta[property=og:description]").first().attr("content")

        Log.e("Repository", "$imgUrl, $description")

        return NewsBody(imgUrl, description)
    }
}