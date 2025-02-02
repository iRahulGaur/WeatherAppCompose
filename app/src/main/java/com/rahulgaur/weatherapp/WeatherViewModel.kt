package com.rahulgaur.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulgaur.weatherapp.api.NetworkResponse
import com.rahulgaur.weatherapp.api.RetrofitInstance
import com.rahulgaur.weatherapp.api.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val retrofit = RetrofitInstance.weatherAPI
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherResponse>>()
    val weatherResult: LiveData<NetworkResponse<WeatherResponse>> = _weatherResult

    fun getData(city: String) {
        _weatherResult.value = NetworkResponse.Loading(isLoading = true)
        viewModelScope.launch {
            try {
                val response = retrofit.getWeather(Constants.WEATHER_API_KEY, city)
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _weatherResult.value = NetworkResponse.Success(response.body()!!)
                        _weatherResult.value = NetworkResponse.Loading(isLoading = false)
                    } else {
                        _weatherResult.value = NetworkResponse.Error("API data is null")
                        _weatherResult.value = NetworkResponse.Loading(isLoading = false)
                    }
                } else {
                    _weatherResult.value = NetworkResponse.Error("Fail to load data!!")
                    _weatherResult.value = NetworkResponse.Loading(isLoading = false)
                }
            } catch (e: Exception) {
                _weatherResult.value =
                    NetworkResponse.Error("Exception when running API = ${e.message}")
                _weatherResult.value = NetworkResponse.Loading(isLoading = false)
            }
        }
    }

}