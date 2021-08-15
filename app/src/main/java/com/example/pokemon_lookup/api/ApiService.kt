package com.example.pokemon_lookup.api

import com.example.pokemon_lookup.models.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon")
    suspend fun getPokemonList(@Query("limit") limit : Int , @Query("offset")offset: Int): Response<PokemonResponse>
}