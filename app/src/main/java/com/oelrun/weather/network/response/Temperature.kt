package com.oelrun.weather.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Temperature (
    @SerialName("min")
    val min: Float,
    @SerialName("max")
    val max: Float
)