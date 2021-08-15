package com.example.pokemon_lookup.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.pokemon_lookup.databinding.ActivityMainBinding
import com.example.pokemon_lookup.repository.PokemonRepository
import com.example.pokemon_lookup.ui.viewmodels.PokemonViewModel
import com.example.pokemon_lookup.ui.viewmodels.PokemonViewModelProviderFactory


class MainActivity : AppCompatActivity() {


    private lateinit var _binding: ActivityMainBinding
    private val binding get() = _binding

    lateinit var viewModel: PokemonViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val pokemonRepository = PokemonRepository()
        val viewModelProviderFactory = PokemonViewModelProviderFactory(application, pokemonRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(PokemonViewModel::class.java)
    }
}