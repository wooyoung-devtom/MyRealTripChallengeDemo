package com.example.myrealtripchallengedemo.data

import io.reactivex.Single

interface RssRepository {

    fun getRssData(hl: String, gl: String, ceid: String): Single<String>
}