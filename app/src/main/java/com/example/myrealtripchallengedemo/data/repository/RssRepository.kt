package com.example.myrealtripchallengedemo.data.repository

import com.example.myrealtripchallengedemo.data.dto.NewsBody
import com.example.myrealtripchallengedemo.data.dto.RssFeed
import io.reactivex.Single

interface RssRepository {

    fun getRssData(hl: String, gl: String, ceid: String): Single<RssFeed>

    fun getNewsData(url: String): NewsBody
}