package com.example.myrealtripchallengedemo.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface RssHtmlService {
    @GET
    fun getHtmlString(
        @Url url: String
    ): Single<String>
}