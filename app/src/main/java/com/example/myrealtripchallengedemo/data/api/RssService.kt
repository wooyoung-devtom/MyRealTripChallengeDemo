package com.example.myrealtripchallengedemo.data.api

import com.example.myrealtripchallengedemo.data.dto.RssFeed
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface RssService {

    @GET("/rss")
    fun getRssData(
        @Query("hl") hl: String,
        @Query("gl") gl: String,
        @Query("ceid") ceid: String
    ): Single<RssFeed>
}