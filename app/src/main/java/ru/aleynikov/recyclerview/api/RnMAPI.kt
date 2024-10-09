package ru.aleynikov.recyclerview.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RnMAPI {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("count") count: Int,
        @Query("pages") pages: Int,
        @Query("status") status: String = "",
        @Query("gender") gender: String = ""
    ): ResponseCharactersDto

    companion object {
        private const val URL = "https://rickandmortyapi.com/api/"
        fun getInstance(): RnMAPI {
            return Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(RnMAPI::class.java)
        }
    }
}