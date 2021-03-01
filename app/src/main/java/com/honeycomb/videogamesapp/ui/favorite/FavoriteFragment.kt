package com.honeycomb.videogamesapp.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.honeycomb.videogamesapp.R
import com.honeycomb.videogamesapp.adapter.FavoriteAdapter
import com.honeycomb.videogamesapp.data.RapidGame
import com.honeycomb.videogamesapp.databinding.FragmentFavoriteBinding
import com.honeycomb.videogamesapp.utils.DataStore.Companion.GetList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite), FavoriteAdapter.OnItemClickListener {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentFavoriteBinding.bind(view)

        if(GetList().size > 0){
            binding.recyclerView.setHasFixedSize(true)
            binding.recyclerView.adapter = FavoriteAdapter(GetList()) {
                toDetailFragment(it)
            }
        }
    }
    fun toDetailFragment(game: RapidGame)
    {
        val action = FavoriteFragmentDirections.actionFavoriteFragmentToGameDetailsFragment(game)
        findNavController().navigate(action)
    }

    override fun onItemClick(game: RapidGame) {
        TODO("Not yet implemented")
    }
}