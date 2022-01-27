package com.mahmoudroid.rickmorty.data

import com.mahmoudroid.rickmorty.model.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getCharacters(@Query("page") query: Int): Result
}