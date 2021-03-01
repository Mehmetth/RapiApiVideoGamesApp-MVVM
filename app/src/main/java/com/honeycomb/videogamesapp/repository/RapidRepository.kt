package com.honeycomb.videogamesapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.honeycomb.videogamesapp.api.RapidApi
import com.honeycomb.videogamesapp.data.RapidPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RapidRepository @Inject constructor(private val rapidApi: RapidApi) {

    fun getGamesResults() =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RapidPagingSource(rapidApi) }
        ).liveData
}