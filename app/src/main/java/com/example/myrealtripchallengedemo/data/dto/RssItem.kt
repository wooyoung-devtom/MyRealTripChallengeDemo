package com.example.myrealtripchallengedemo.data.dto

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class RssItem constructor(
    @field:Element(name = "title")
    @param:Element(name = "title")
    val title: String? = null,
    @field:Element(name = "link")
    @param:Element(name = "link")
    val link: String? = null
)