package com.rahulgaur.weatherapp

import android.util.Log
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
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = retrofit.getWeather(Constants.WEATHER_API_KEY, city)
                Log.e("TAG", "This is the response code = ${response.code()}")
                Log.e("TAG", "This is the response message = ${response.message()}")
                Log.e("TAG", "This is the response body = ${response.body()}")
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        _weatherResult.value = NetworkResponse.Success(response.body()!!)
                    } else {
                        _weatherResult.value = NetworkResponse.Error("API data is null")
                    }
                } else {
                    _weatherResult.value = NetworkResponse.Error("Fail to load data!!")
                }
            } catch (e: Exception) {
                _weatherResult.value =
                    NetworkResponse.Error("Exception when running API = ${e.message}")
            }
        }
    }

}