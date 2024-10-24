package com.hemanth.weatherapplication.data.model


import com.google.gson.annotations.SerializedName

data class CloudsModel(
    @SerializedName("all")
    val all: Int? = 0
)