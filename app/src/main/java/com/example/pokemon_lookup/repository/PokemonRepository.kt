package com.example.pokemon_lookup.repository

import com.example.pokemon_lookup.api.RetrofitInstance
import com.example.pokemon_lookup.utils.Constants.Companion.MAX_POKEMONS_LOADED

class PokemonRepository {

    suspend fun getPokemonList(offset: Int) = RetrofitInstance.api.getPokemonList(MAX_POKEMONS_LOADED, offset)
}