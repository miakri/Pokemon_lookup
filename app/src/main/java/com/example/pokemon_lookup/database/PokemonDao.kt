package com.example.pokemon_lookup.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemonEntity: PokemonCacheEntity): Long

    @Query("SELECT * FROM pokemons")
    suspend fun get(): List<PokemonCacheEntity>
}