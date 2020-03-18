package com.example.myrealtripchallengedemo.data.repository

import com.example.myrealtripchallengedemo.data.api.RssService
import com.example.myrealtripchallengedemo.data.dto.RssBase
import io.reactivex.Single

class RssRepositoryImpl (
    private val rssService: RssService
): RssRepository {

    override fun getRssData(hl: String, gl: String, ceid: String): Single<String> {
        return rssService.getRssData(hl, gl, ceid)
    }

}