package com.honeycomb.videogamesapp.api

import com.honeycomb.videogamesapp.data.RapidDetailGame
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface RapidApi {

    companion object {
        const val BASE_URL = "https://rawg-video-games-database.p.rapidapi.com/"
        const val API_KEY = "e52d6fc777msh9ba9a7129cfcb43p15930fjsnaf05daf7ba57"
        const val HOST= "rawg-video-games-database.p.rapidapi.com"
    }

    @Headers("x-rapidapi-key: $API_KEY","x-rapidapi-host: $HOST")
    @GET("games")
    suspend fun getAllGames(
        @Query("page") page: Int
    ): RapidResponse

    @Headers("x-rapidapi-key: $API_KEY","x-rapidapi-host: $HOST")
    @GET("games/{id}")
    fun getGameDetail(
            @Path("id") id: String
    ): Call<RapidDetailGame>
}