package com.example.myrealtripchallengedemo.data.dto

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementArray
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
data class RssChannel(
    @Element
    val generator: String,
    @Element
    val title: String,
    @Element
    val link: String,
    @Element
    val language: String,
    @Element
    val webMaster: String,
    @Element
    val copyright: String,
    @Element
    val lastBuildDate: String,
    @Element
    val description: String,
    @ElementArray
    val item: List<RssItem>
)