package com.honeycomb.videogamesapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RapidGame(
    val id: String,
    val name: String?,
    val released: String?,
    val background_image: String?,
    val rating: String?
) : Parcelable