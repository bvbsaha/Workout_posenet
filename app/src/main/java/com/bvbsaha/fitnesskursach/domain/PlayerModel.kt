package com.bvbsaha.fitnesskursach.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class PlayerModel(val score: String= "0", val type : String = "squat") : Parcelable