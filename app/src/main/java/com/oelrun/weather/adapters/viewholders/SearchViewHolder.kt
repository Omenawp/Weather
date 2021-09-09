package com.oelrun.weather.adapters.viewholders

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.oelrun.weather.R
import com.oelrun.weather.database.entity.relation.CityTemp
import com.oelrun.weather.databinding.ListItemSearchBinding
import com.oelrun.weather.utils.setBackgroundWeather
import kotlin.math.roundToInt

class SearchViewHolder(private val binding: ListItemSearchBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CityTemp, clickListener: (pos: Int) -> Unit) {
        binding.cityName.text = item.cityName
        binding.temp.text = "${item.temperature.roundToInt()}°"

        if(item.cityId != 0L) {
            val drawable = if(item.save) R.drawable.ic_delete else R.drawable.ic_add
            binding.btnAction.visibility = View.VISIBLE
            binding.btnAction.setImageResource(drawable)
            binding.btnAction.setOnClickListener {
                clickListener(adapterPosition)
            }
        } else {
            binding.btnAction.visibility = View.GONE
        }

        binding.root.setBackgroundWeather(item.iconCode)

    }
}