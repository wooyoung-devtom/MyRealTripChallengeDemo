package com.example.myrealtripchallengedemo.data.dto

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementArray
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "channel", strict = false)
data class RssChannel constructor(
    @field:Element(name = "language")
    @param:Element(name = "language")
    val language: String? = null,
    @field:Element(name = "copyright")
    @param:Element(name = "copyright")
    val copyright: String? = null,
    @field:Element(name = "description")
    @param:Element(name = "description")
    val description: String? = null,
    @field:ElementList(name = "item", inline = true, required = false)
    @param:ElementList(name = "item", inline = true, required = false)
    val item: List<RssItem>? = null
)