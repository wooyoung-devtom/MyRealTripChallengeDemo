package com.example.myrealtripchallengedemo.data.dto

data class NewsDetailBody(
    val imgUrl: String? = null,
    val description: String? = null,
    val newsBody: NewsBody? = null,
    val keyWord: KeyWord? = null
) {
    data class KeyWord(
        val firstKey: String? = null,
        val secondKey: String? = null,
        val thirdKey: String? = null
    )
}