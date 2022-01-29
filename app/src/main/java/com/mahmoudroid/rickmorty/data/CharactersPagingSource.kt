package com.mahmoudroid.rickmorty.data

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mahmoudroid.rickmorty.model.Characters

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

            if (response?.info?.next != null) {
                val uri = Uri.parse(response.info.next!!)
                var nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data =
                response.charactersList,
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            PagingSource.LoadResult.Error(e)
        }
    }
}



