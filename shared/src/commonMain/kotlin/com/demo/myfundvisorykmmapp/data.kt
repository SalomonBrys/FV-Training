package com.demo.myfundvisorykmmapp

import kotlinx.serialization.Serializable


@Serializable
data class HNItem(
    val id: Long,
    val type: String,
    val time: Long,
    val text: String? = null,
    val kids: List<Long> = emptyList(),
    val url: String? = null,
    val title: String,
    val descendants: Int = 0
)
