package com.example.myrealtripchallengedemo.data.dto

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class RssFeed constructor(
    @field:Element(name = "channel")
    @param:Element(name = "channel")
    val channel: RssChannel? = null
)