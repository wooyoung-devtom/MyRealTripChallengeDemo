package com.example.myrealtripchallengedemo.data.repository

import com.example.myrealtripchallengedemo.data.dto.RssBase
import io.reactivex.Single

interface RssRepository {

    fun getRssData(hl: String, gl: String, ceid: String): Single<String>
}