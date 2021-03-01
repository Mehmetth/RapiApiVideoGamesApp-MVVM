package com.honeycomb.videogamesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.honeycomb.videogamesapp.R
import com.honeycomb.videogamesapp.data.RapidGame

class DotIndicatorPager2Adapter(val gameList: MutableList<RapidGame>, val clickListener: (RapidGame) -> Unit ) :
    RecyclerView.Adapter<DotIndicatorPager2Adapter.ModelViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.material_page, parent, false)
        return ModelViewHolder(view)
    }

    override fun getItemCount() = 3

    override fun onBindViewHolder(holder: ModelViewHolder, position: Int) {

        holder.bindItems(gameList.get(position))

        val item : RapidGame = gameList[position]
        holder.itemView.setOnClickListener { clickListener(item) }
    }
    class ModelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val countryName: ImageView = view.findViewById(R.id.item_image)

        fun bindItems(item: RapidGame) {
            Glide.with(itemView)
                .load(item.background_image)
                .error(R.drawable.ic_error)
                .into(countryName)
        }
    }
    interface OnItemClickListener {
        fun onItemClick(game: RapidGame)
    }
}