package com.example.myrealtripchallengedemo.data.dto

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class RssBase(
    @Element
    val channel: RssChannel
)