package com.example.pokemon_lookup.database

import com.example.pokemon_lookup.models.Pokemon
import com.example.pokemon_lookup.utils.EntityMapper
import javax.inject.Inject

class CacheMapper
@Inject constructor() : EntityMapper<PokemonCacheEntity, Pokemon>{
    override fun mapFromEntity(entity: PokemonCacheEntity): Pokemon {
        TODO("Not yet implemented")
    }

    override fun mapToEntity(domainModel: Pokemon): PokemonCacheEntity {
        TODO("Not yet implemented")
    }
}