package com.example.pokemon_lookup.ui


import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon_lookup.R
import com.example.pokemon_lookup.adapters.PokemonAdapter
import com.example.pokemon_lookup.databinding.FragmentHomeBinding
import com.example.pokemon_lookup.ui.viewmodels.PokemonViewModel
import com.example.pokemon_lookup.utils.Constants.Companion.MAX_POKEMONS_LOADED
import com.example.pokemon_lookup.utils.Resource


class HomeFragment: Fragment(R.layout.fragment_home) {

    lateinit var viewModel: PokemonViewModel
    lateinit var pokemonAdapter: PokemonAdapter
    private lateinit var _binding: FragmentHomeBinding
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        setupRecyclerView()

        setupTheObservers()
    }

    private fun setupTheObservers(){
        viewModel.pokemons.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success ->{
                    hideProgressbar()
                    response.data?.let {pokemonResponse ->
                        pokemonAdapter.differ.submitList(pokemonResponse.results.toList())
                        val totalItemsLoaded = pokemonAdapter.differ.currentList.size
                        isLastPage = viewModel.pokemosTotal == totalItemsLoaded
                        if (isLastPage){
                            binding.rvPokemonHome.setPadding(0,0,0,0)
                        }
                    }
                }
                is Resource.Error ->{
                    hideProgressbar()
                    response.message?.let {
                        Toast.makeText(activity, "An error occurred: $it", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading ->{
                    showProgressBar()
                }
            }
        })
    }

    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false

    private fun hideProgressbar(){
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }
    private fun showProgressBar(){
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun setupRecyclerView() {
        pokemonAdapter = PokemonAdapter()
        binding.rvPokemonHome.apply {
            adapter = pokemonAdapter
            layoutManager = GridLayoutManager(requireContext(), 3)
            addOnScrollListener(scrollListener)
        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotAtLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= MAX_POKEMONS_LOADED
            val shouldPaginate = isNotLoadingAndNotAtLastPage && isAtLastItem && isNotAtBeginning
                    && isTotalMoreThanVisible && isScrolling
            if(shouldPaginate){
                viewModel.getPokemons(viewModel.pokemonOffset)
                isScrolling = false
            }
        }
    }



}