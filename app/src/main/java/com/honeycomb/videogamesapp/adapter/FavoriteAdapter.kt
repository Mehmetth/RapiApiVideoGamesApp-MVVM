package com.honeycomb.videogamesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.honeycomb.videogamesapp.R
import com.honeycomb.videogamesapp.data.RapidGame

class FavoriteAdapter(val favoriteList: MutableList<RapidGame>, val clickListener: (RapidGame) -> Unit) :
    RecyclerView.Adapter<FavoriteAdapter.ModelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rapid_photo, parent, false)
        return ModelViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {
        holder.bindItems(favoriteList.get(position))

        val item : RapidGame = favoriteList[position]
        holder.itemView?.setOnClickListener { clickListener(item) }
    }

    class ModelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val game_name_text_view: TextView = view.findViewById(R.id.game_name_text_view)
        val game_rating_released_text_view: TextView = view.findViewById(R.id.game_rating_released_text_view)
        val game_image_view: ImageView = view.findViewById(R.id.game_image_view)

        fun bindItems(item: RapidGame) {
            game_name_text_view.setText(item.name)
            game_rating_released_text_view.setText(item.rating + " - " + item.released)
            Glide.with(itemView)
                .load(item.background_image)
                .error(R.drawable.ic_error)
                .into(game_image_view)
            game_image_view.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    interface OnItemClickListener {
        fun onItemClick(game: RapidGame)
    }
}