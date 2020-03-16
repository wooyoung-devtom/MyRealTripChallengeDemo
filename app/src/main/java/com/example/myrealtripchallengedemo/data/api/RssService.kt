package com.example.myrealtripchallengedemo.data.api

import com.example.myrealtripchallengedemo.data.dto.RssBase
import io.reactivex.Single
import retrofit2.http.GET

interface RssService {

    @GET("rss")
    fun getRssData(): Single<RssBase>
}