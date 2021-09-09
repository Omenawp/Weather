package com.oelrun.weather.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailResponse (
    @SerialName("description")
    val description: String,
    @SerialName("icon")
    val icon: String
)