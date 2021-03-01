package com.honeycomb.videogamesapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RapidDetailGame(
    val id: String,
    val name: String?,
    val released: String?,
    val metacritic: String?,
    val rating: String?,
    val description: String?,
    val background_image: String?
) : Parcelable