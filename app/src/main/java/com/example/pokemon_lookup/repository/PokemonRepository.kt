package com.example.pokemon_lookup.repository

import com.example.pokemon_lookup.api.RetrofitInstance

class PokemonRepository {

    suspend fun getPokemonList(offset: Int) = RetrofitInstance.api.getPokemonList(20, offset)
}