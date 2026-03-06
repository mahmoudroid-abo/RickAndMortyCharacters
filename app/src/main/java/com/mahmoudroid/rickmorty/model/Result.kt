package com.mahmoudroid.rickmorty.model
import java.io.Serializable
data class Result(
    val info: Info,
    val results: List<Characters>

): Serializable