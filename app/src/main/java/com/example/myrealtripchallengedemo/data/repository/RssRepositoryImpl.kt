package com.example.myrealtripchallengedemo.data.repository

import com.example.myrealtripchallengedemo.data.api.RssHtmlService
import com.example.myrealtripchallengedemo.data.api.RssService
import com.example.myrealtripchallengedemo.data.dto.RssFeed
import io.reactivex.Single

class RssRepositoryImpl (
    private val rssService: RssService,
    private val rssHtmlService: RssHtmlService
): RssRepository {

    override fun getRssData(hl: String, gl: String, ceid: String): Single<RssFeed> {
        return rssService.getRssData(hl, gl, ceid)
    }

    override fun getNewsData(url: String): Single<String> {
        return rssHtmlService.getHtmlString(url)
    }
}