package com.honeycomb.videogamesapp.data

import androidx.paging.PagingSource
import com.honeycomb.videogamesapp.api.RapidApi
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class RapidPagingSource(
    private val rapidApi: RapidApi
) : PagingSource<Int, RapidGame>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RapidGame> {
        val position = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {
            val response = rapidApi.getAllGames(position)
            val games = response.results

            LoadResult.Page(
                data = games,
                prevKey = if (position == UNSPLASH_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (games.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}