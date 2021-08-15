package com.example.pokemon_lookup.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokemon_lookup.models.PokemonResponse
import com.example.pokemon_lookup.repository.PokemonRepository
import com.example.pokemon_lookup.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class PokemonViewModel(
    app: Application,
    val pokemonRepository: PokemonRepository
) : AndroidViewModel(app) {

    private val _pokemons: MutableLiveData<Resource<PokemonResponse>> = MutableLiveData()
    val pokemons: LiveData<Resource<PokemonResponse>> = _pokemons
    var pokemonOffset = 0
    var pokemonResponse: PokemonResponse? = null
    var pokemosTotal = 0

    init {
        getPokemons(0)
    }


    fun getPokemons(offset: Int) = viewModelScope.launch {
        safeGetPokemonsCall(offset)
    }

    private suspend fun safeGetPokemonsCall(offset: Int) {
        _pokemons.postValue(Resource.Loading())
        val response = pokemonRepository.getPokemonList(pokemonOffset)

        _pokemons.postValue(handlePokemonsResponse(response))
    }

    private fun handlePokemonsResponse(response: Response<PokemonResponse>): Resource<PokemonResponse>{
        if(response.isSuccessful){
            response.body()?.let { resultResponse->
                pokemonOffset += 20
                pokemosTotal = resultResponse.count
                if (pokemonResponse == null){
                    pokemonResponse = resultResponse
                } else{
                    val oldPokemons = pokemonResponse?.results
                    val newsPokemons = resultResponse.results
                    oldPokemons?.addAll(newsPokemons)
                }
                return Resource.Success(pokemonResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

}