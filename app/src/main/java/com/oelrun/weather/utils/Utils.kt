package com.oelrun.weather.utils

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.oelrun.weather.R

fun ImageView.setWeather(code: String) {
    val icon = when(code) {
        "01d" -> R.drawable.ic_sun
        "01n" -> R.drawable.ic_clear_night
        "02d" -> R.drawable.ic_cloudy_day
        "02n" -> R.drawable.ic_cloudy_night
        "10d" -> R.drawable.ic_rainy_day
        "10n" -> R.drawable.ic_rainy_night
        else -> {
            when(code.dropLast(1)) {
                "03" -> R.drawable.ic_cloudy
                "04" -> R.drawable.ic_cloudy_heavy
                "09" -> R.drawable.ic_rainy
                "11" -> R.drawable.ic_storm
                "13" -> R.drawable.ic_snow
                else -> R.drawable.ic_mist
            }
        }
    }

    this.setImageDrawable(resources.getDrawable(icon))
}

fun ConstraintLayout.setBackgroundWeather(code: String?) {
    code ?: return
    val icon = when(code) {
        "01d" -> R.drawable.bgr_clear_day
        "01n" -> R.drawable.bgr_clear_night
        "02d" -> R.drawable.bgr_cloudy_day
        "10d" -> R.drawable.bgr_cloudy_day
        "11d" -> R.drawable.bgr_storm_day
        "11n" -> R.drawable.bgr_storm_night
        else -> {
            when(code.drop(2)) {
                "n" -> R.drawable.bgr_cloudy_night
                else -> R.drawable.bgr_rain_day
            }
        }
    }

    this.setBackgroundResource(icon)
}