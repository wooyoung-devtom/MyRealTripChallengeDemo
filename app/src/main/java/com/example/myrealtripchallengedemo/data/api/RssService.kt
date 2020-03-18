package com.example.myrealtripchallengedemo.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RssService {

    @GET("/rss")
    fun getRssData(
        @Query("hl") hl: String,
        @Query("gl") gl: String,
        @Query("ceid") ceid: String
    ): Single<String>
}