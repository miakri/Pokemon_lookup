package com.example.pokemon_lookup.models

data class PokemonResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: MutableList<Pokemon>
)