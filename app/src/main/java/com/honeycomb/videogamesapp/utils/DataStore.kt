package com.honeycomb.videogamesapp.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.honeycomb.videogamesapp.data.RapidGame
import java.io.IOException
import java.lang.Exception
import java.lang.RuntimeException

class DataStore {

    companion object {

        var mPrefs: SharedPreferences? = null

        fun SetLists(list:ArrayList<RapidGame>){
            try {
                val gson = Gson()
                val editor = mPrefs?.edit()
                val json = gson.toJson(list)//converting list to Json
                editor?.putString("favoriteList",json)
                editor?.apply()
            }
            catch (ex:Exception) {
                throw RuntimeException()
            }
        }

        fun GetList(): ArrayList<RapidGame> {
            return try {
                val emptyList = Gson().toJson(ArrayList<RapidGame>())
                Gson().fromJson(
                    mPrefs?.getString("favoriteList", emptyList),
                    object : TypeToken<ArrayList<RapidGame>>() {
                    }.type
                )
            } catch (ex: Exception) {
                ArrayList<RapidGame>()
                throw RuntimeException()
            }
        }
    }
}