package com.example.myrealtripchallengedemo.data.dto

data class NewsDetailBody(
    val title: String? = null,
    val link: String? = null,
    val imgUrl: String? = null,
    val description: String? = null,
    val keyWord: KeyWord? = null
) {
    data class KeyWord(
        val firstKey: String? = null,
        val secondKey: String? = null,
        val thirdKey: String? = null
    )
}