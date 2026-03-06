package com.mahmoudroid.rickmorty.data

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mahmoudroid.rickmorty.model.Characters
import androidx.core.net.toUri

private const val TAG = "CharactersPagingSource"
class CharactersPagingSource(val apiService: ApiService) :
    PagingSource<Int, Characters>() {

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, Characters>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Characters> {
        return try {
            val nextPage: Int = params.key ?: FIRST_PAGE_INDEX
            val response = apiService.getCharacters(nextPage)

            var nextPageNumber: Int? = null
            response.info.next?.let {
                val uri = response.info.next.toUri()
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }
            var prevPageNumber: Int? = null
            response.info.prev?.let {
                val uri = response.info.prev.toUri()
                val prevPageQuery = uri.getQueryParameter("page")

                prevPageNumber = prevPageQuery?.toInt()
            }

            LoadResult.Page(
                data = response.results,
                prevKey = prevPageNumber,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}