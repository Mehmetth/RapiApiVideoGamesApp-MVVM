package com.honeycomb.videogamesapp.ui.gamesdetail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.honeycomb.videogamesapp.R
import com.honeycomb.videogamesapp.api.RapidApi
import com.honeycomb.videogamesapp.api.RapidApi.Companion.BASE_URL
import com.honeycomb.videogamesapp.data.RapidDetailGame
import com.honeycomb.videogamesapp.data.RapidGame
import com.honeycomb.videogamesapp.databinding.FragmentGameDetailsBinding
import com.honeycomb.videogamesapp.utils.DataStore
import com.honeycomb.videogamesapp.utils.DataStore.Companion.GetList
import kotlinx.android.synthetic.main.fragment_game_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameDetailsFragment : Fragment(R.layout.fragment_game_details) {

    private val args by navArgs<GameDetailsFragmentArgs>()
    var likeButtonControl = false
    var rapidDetailGame: RapidDetailGame? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentGameDetailsBinding.bind(view)

        binding.apply {

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(RapidApi::class.java)
            val call = service.getGameDetail(args.game.id)
            call.enqueue(object : Callback<RapidDetailGame> {
                override fun onResponse(call: Call<RapidDetailGame>, response: Response<RapidDetailGame>) {
                    if (response.code() == 200) {

                        rapidDetailGame = response.body()!!

                        Glide.with(this@GameDetailsFragment)
                            .load(rapidDetailGame!!.background_image)
                            .error(R.drawable.ic_error)
                            .into(gameImageView)

                        gameNameTextView.text = rapidDetailGame!!.name
                        gameReleasedTextView.text = rapidDetailGame!!.released
                        gameMetacriticTextView.text = rapidDetailGame!!.metacritic
                        var description = rapidDetailGame!!.description.toString().replace("<p>","")
                        description = description.replace("<br/>","")
                        description = description.replace("<br />","")
                        description = description.replace("</p>","")
                        gameDescriptionTextView.text = description

                        LoadLikeButton(rapidDetailGame!!,likeImageButton)
                    }
                }
                override fun onFailure(call: Call<RapidDetailGame>, t: Throwable) {
                    Log.d("GameDetailsFragment","onFailure:  $t")
                }
            })
        }

        like_image_button.setOnClickListener {

            if(likeButtonControl){
                like_image_button.setBackgroundResource(R.drawable.ic_unlike)
                likeButtonControl = false

                DeleteLikeButton(GetList())
            }
            else {
                like_image_button.setBackgroundResource(R.drawable.ic_like)
                likeButtonControl = true

                AddLikeButton(GetList())
            }
        }
    }

    fun LoadLikeButton(game:RapidDetailGame, image: ImageView)
    {
        try {
            for(gameList in GetList())
            {
                if(gameList.id == game.id)
                {
                    image.setBackgroundResource(R.drawable.ic_like)
                    likeButtonControl = true
                }
            }
        }
        catch (ex:Exception){
            throw RuntimeException()
        }
    }

    fun AddLikeButton(list: ArrayList<RapidGame>)
    {
        try {

            if(list.filter { it.id == rapidDetailGame!!.id }.isEmpty())
            {
                list.add(RapidGame(args.game.id,args.game.name,args.game.released,args.game.background_image,args.game.rating))
                DataStore.SetLists(list)
            }
        }
        catch (ex:Exception){
            throw RuntimeException()
        }
    }

    fun DeleteLikeButton(list: ArrayList<RapidGame>)
    {
        try{
            var i = 0
            list.forEachIndexed { index, element ->
                if(element.id == rapidDetailGame!!.id)
                {
                    i = index
                }
            }
            if( i != -1)
            {
                list.removeAt(i)
                DataStore.SetLists(list)
            }
        }
        catch (ex:Exception){
            throw RuntimeException()
        }
    }
}