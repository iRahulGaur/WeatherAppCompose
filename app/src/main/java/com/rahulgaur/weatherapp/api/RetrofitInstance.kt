package com.rahulgaur.weatherapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

//    https://api.weatherapi.com/v1/current.json?key=f441167762284cf296c83100252801&q=London

    private const val BASE_URL = "https://api.weatherapi.com"

    private fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    val weatherAPI: WeatherAPI = getInstance().create(WeatherAPI::class.java)

}