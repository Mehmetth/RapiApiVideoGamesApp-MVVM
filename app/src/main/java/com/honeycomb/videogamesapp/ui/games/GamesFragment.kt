package com.honeycomb.videogamesapp.ui.games

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.honeycomb.videogamesapp.R
import com.honeycomb.videogamesapp.adapter.DotIndicatorPager2Adapter
import com.honeycomb.videogamesapp.adapter.RapidGameAdapter
import com.honeycomb.videogamesapp.adapter.RapidGamesLoadStateAdapter
import com.honeycomb.videogamesapp.data.GamesViewModel
import com.honeycomb.videogamesapp.data.RapidGame
import com.honeycomb.videogamesapp.databinding.FragmentGamesBinding
import com.honeycomb.videogamesapp.utils.Constant.Companion.gamesList
import com.honeycomb.videogamesapp.utils.ZoomOutPageTransformer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_games.*

@AndroidEntryPoint
class GamesFragment : Fragment(R.layout.fragment_games),
    RapidGameAdapter.OnItemClickListener, DotIndicatorPager2Adapter.OnItemClickListener{

    private val viewModel by viewModels<GamesViewModel>()

    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!

    var viewPagerAdapter: DotIndicatorPager2Adapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentGamesBinding.bind(view)

        val adapter = RapidGameAdapter(this)

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = RapidGamesLoadStateAdapter { adapter.retry() },
                footer = RapidGamesLoadStateAdapter { adapter.retry() }
            )
            buttonRetry.setOnClickListener { adapter.retry() }
        }

        viewModel.games.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                loadViewPager()

                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }

        setHasOptionsMenu(true)

        game_search_edit_text.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().length >= 3)
                {
                    viewModel.searchNameChanged(s.toString())
                }
            }
        })
    }

    override fun onItemClick(game: RapidGame) {
        toDetailFragment(game)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun loadViewPager()
    {
        try{
            if(gamesList.size > 0 && gamesList.size != 0)
            {
                binding.viewPager2.adapter = viewPagerAdapter
                binding.viewPager2?.adapter = DotIndicatorPager2Adapter(gamesList) {
                    toDetailFragment(it)
                }
                val zoomOutPageTransformer = ZoomOutPageTransformer()
                binding.viewPager2.setPageTransformer { page, position ->
                    zoomOutPageTransformer.transformPage(page, position)
                }
                binding.dotsIndicator.setViewPager2(binding.viewPager2)
            }
        }
        catch (ex:Exception){
            throw RuntimeException()
        }
    }

    fun toDetailFragment(game: RapidGame)
    {
        val action = GamesFragmentDirections.actionGamesFragmentToGameDetailsFragment(game)
        findNavController().navigate(action)
    }
}