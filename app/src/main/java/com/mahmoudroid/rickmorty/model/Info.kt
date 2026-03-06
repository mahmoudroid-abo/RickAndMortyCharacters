package com.mahmoudroid.rickmorty.model
import java.io.Serializable
data class Info(
    val count: Int?,
    val next: String?,
    val pages: String?,
    val prev: String?
) : Serializable