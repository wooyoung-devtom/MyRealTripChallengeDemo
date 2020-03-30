package com.example.myrealtripchallengedemo.data.dto

data class NewsBody(
    val title: String,
    val link: String,
    val thumbnailImg: String,
    val text: String,
    val descriptionArr: MutableList<String>,
    val keyword: KeyWord? = null
) {
    data class KeyWord(
        val firstKey: String,
        val secondKey: String,
        val thirdKey: String
    )
}