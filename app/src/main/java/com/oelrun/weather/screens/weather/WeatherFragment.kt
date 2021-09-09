package com.oelrun.weather.screens.weather

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.oelrun.weather.adapters.WeatherAdapter
import com.oelrun.weather.databinding.FragmentWeatherBinding
import com.oelrun.weather.utils.setBackgroundWeather

class WeatherFragment: Fragment() {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!
    private val weatherViewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)

        weatherViewModel.getWeatherData()

        val adapter = WeatherAdapter()
        binding.pager.adapter = adapter

        binding.pager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val item = weatherViewModel.weatherData.value?.get(position)
                weatherViewModel.weatherData.value?.let { list ->
                    binding.locationView.apply {
                        this.position = position
                        this.size = list.size
                        this.invalidate()
                    }
                }
                binding.cityName.text = item?.cityName
                binding.root.setBackgroundWeather(item?.current?.iconCode)
                if(item != null && item.cityId != 0L) {
                    weatherViewModel.updateWeather(item.cityId, item.lat, item.lon, position)
                }
            }
        })

        weatherViewModel.weatherData.observe(viewLifecycleOwner, {
            if(it?.isEmpty() == true) {
                binding.locationView.visibility = View.GONE
                binding.pager.visibility = View.GONE
                binding.errorPicture.visibility = View.VISIBLE
                binding.errorText.visibility = View.VISIBLE
            } else {
                adapter.submitList(it)
                binding.errorPicture.visibility = View.GONE
                binding.errorText.visibility = View.GONE
                binding.locationView.visibility = View.VISIBLE
                binding.pager.visibility = View.VISIBLE
            }
        })

        weatherViewModel.errorMessage.observe(viewLifecycleOwner, {
            it?.let{
                Toast.makeText(this.requireContext(), it, Toast.LENGTH_LONG).show()
            }
        })

        binding.addIcon.setOnClickListener {
            this.findNavController().navigate(WeatherFragmentDirections
                .actionWeatherFragmentToSearchFragment())
            weatherViewModel.clearAll()
        }

        weatherViewModel.loading.observe(viewLifecycleOwner, {
            binding.loader.visibility = if(it) View.VISIBLE else View.GONE
        })

        updateLocation()

        return binding.root
    }

    private fun updateLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val listener = LocationListener {
                weatherViewModel.updateLocation(it.latitude.toFloat(), it.longitude.toFloat())
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                0L, 0f, listener)
            weatherViewModel.updated.observe(viewLifecycleOwner, {
                if(it) {
                    locationManager.removeUpdates(listener)
                }
            })
        } else {
            weatherViewModel.deleteLocation()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}