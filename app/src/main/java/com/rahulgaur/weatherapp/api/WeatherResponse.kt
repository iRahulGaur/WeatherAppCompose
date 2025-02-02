package com.rahulgaur.weatherapp.api

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class WeatherResponse(
    @SerializedName("current") val current: Current?,
    @SerializedName("location") val location: Location?
) {
    @Keep
    data class Current(
        @SerializedName("cloud") val cloud: String?, // 100
        @SerializedName("condition") val condition: Condition?,
        @SerializedName("dewpoString_c") val dewpoStringC: String?, // 0.5
        @SerializedName("dewpoString_f") val dewpoStringF: String?, // 33.0
        @SerializedName("feelslike_c") val feelslikeC: String?, // -0.7
        @SerializedName("feelslike_f") val feelslikeF: String?, // 30.8
        @SerializedName("gust_kph") val gustKph: String?, // 10.4
        @SerializedName("gust_mph") val gustMph: String?, // 6.5
        @SerializedName("heatindex_c") val heatindexC: String?, // 2.6
        @SerializedName("heatindex_f") val heatindexF: String?, // 36.6
        @SerializedName("humidity") val humidity: String?, // 93
        @SerializedName("is_day") val isDay: String?, // 0
        @SerializedName("last_updated") val lastUpdated: String?, // 2025-02-02 01:30
        @SerializedName("last_updated_epoch") val lastUpdatedEpoch: String?, // 1738459800
        @SerializedName("precip_in") val precipIn: String?, // 0.0
        @SerializedName("precip_mm") val precipMm: String?, // 0.0
        @SerializedName("pressure_in") val pressureIn: String?, // 30.24
        @SerializedName("pressure_mb") val pressureMb: String?, // 1024.0
        @SerializedName("temp_c") val tempC: String?, // 1.1
        @SerializedName("temp_f") val tempF: String?, // 34.0
        @SerializedName("uv") val uv: String?, // 0.0
        @SerializedName("vis_km") val visKm: String?, // 7.0
        @SerializedName("vis_miles") val visMiles: String?, // 4.0
        @SerializedName("wind_degree") val windDegree: String?, // 147
        @SerializedName("wind_dir") val windDir: String?, // SSE
        @SerializedName("wind_kph") val windKph: String?, // 5.8
        @SerializedName("wind_mph") val windMph: String?, // 3.6
        @SerializedName("windchill_c") val windchillC: String?, // 1.0
        @SerializedName("windchill_f") val windchillF: String? // 33.9
    ) {
        @Keep
        data class Condition(
            @SerializedName("code") val code: String?, // 1030
            @SerializedName("icon") val icon: String?, // //cdn.weatherapi.com/weather/64x64/night/143.png
            @SerializedName("text") val text: String? // Mist
        )
    }

    @Keep
    data class Location(
        @SerializedName("country") val country: String?, // United Kingdom
        @SerializedName("lat") val lat: String?, // 51.5171
        @SerializedName("localtime") val localtime: String?, // 2025-02-02 01:42
        @SerializedName("localtime_epoch") val localtimeEpoch: String?, // 1738460551
        @SerializedName("lon") val lon: String?, // -0.1062
        @SerializedName("name") val name: String?, // London
        @SerializedName("region") val region: String?, // City of London, Greater London
        @SerializedName("tz_id") val tzId: String? // Europe/London
    )
}