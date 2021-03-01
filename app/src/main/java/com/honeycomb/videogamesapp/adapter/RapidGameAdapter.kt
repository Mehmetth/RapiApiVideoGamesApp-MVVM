package com.honeycomb.videogamesapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.honeycomb.videogamesapp.R
import com.honeycomb.videogamesapp.data.RapidGame
import com.honeycomb.videogamesapp.databinding.ItemRapidPhotoBinding
import com.honeycomb.videogamesapp.utils.Constant

class RapidGameAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<RapidGame, RapidGameAdapter.GameHolder>(GAME_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameHolder {
        val binding =
            ItemRapidPhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return GameHolder(binding)
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class GameHolder(private val binding: ItemRapidPhotoBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }
        fun bind(game: RapidGame) {
            binding.apply {
                Glide.with(itemView)
                    .load(game.background_image)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(gameImageView)

                gameNameTextView.text = game.name
                gameRatingReleasedTextView.text = game.rating + " - " + game.released

                Log.d("GamesFragment","H ${Constant.gamesList.size}")
                Log.d("gamesList","${game.id}  ${game.name} ${game.released} ${game.background_image} ${game.rating}")
                if(Constant.gamesList.size < 4)
                {
                    Constant.gamesList.add(RapidGame(game.id,game.name,game.released,game.background_image,game.rating))
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(photo: RapidGame)
    }

    companion object {
        private val GAME_COMPARATOR = object : DiffUtil.ItemCallback<RapidGame>() {
            override fun areItemsTheSame(oldItem: RapidGame, newItem: RapidGame) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: RapidGame, newItem: RapidGame) =
                oldItem == newItem
        }
    }
}