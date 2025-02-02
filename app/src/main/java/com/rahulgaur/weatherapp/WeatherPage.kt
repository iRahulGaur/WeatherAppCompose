package com.rahulgaur.weatherapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.rahulgaur.weatherapp.api.NetworkResponse
import com.rahulgaur.weatherapp.api.WeatherResponse

@Composable
fun WeatherPage(modifier: Modifier = Modifier, weatherViewModel: WeatherViewModel) {

    var city by remember {
        mutableStateOf("")
    }

    val weatherResult = weatherViewModel.weatherResult.observeAsState()

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(value = city, onValueChange = {
                city = it
            }, label = {
                Text(text = "Search for your location")
            }, trailingIcon = {
                IconButton(onClick = {
                    keyboardController?.hide()
                    weatherViewModel.getData(city)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search, contentDescription = "Search icon"
                    )
                }
            }, modifier = Modifier.weight(1.0f), singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        weatherViewModel.getData(city)
                    }
                )
            )
        } //end of search filed row

        when (val result = weatherResult.value) {
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }

            is NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }

            is NetworkResponse.Success -> {
                WeatherResultView(weatherResponse = result.data)
            }

            null -> {}
        }
    }

}

@Composable
fun WeatherResultView(weatherResponse: WeatherResponse) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Loading icon",
                modifier = Modifier.size(40.dp)
            )
            Text(text = weatherResponse.location!!.name!!, fontSize = 30.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = weatherResponse.location.country!!, fontSize = 20.sp, color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "${weatherResponse.current!!.tempC!!} ° c",
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        AsyncImage(
            modifier = Modifier.size(160.dp),
            model = "https:${weatherResponse.current.condition!!.icon!!}".replace(
                "64x64", "128x128"
            ),
            contentDescription = "Weather icon from API"
        )
        Text(
            text = weatherResponse.current.condition.text!!,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(20.dp))

        Card {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("Humidity", weatherResponse.current.humidity!!)
                    WeatherKeyVal("Wind Speed", weatherResponse.current.windKph + " km/h")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("UV", weatherResponse.current.uv!!)
                    WeatherKeyVal("Participation", weatherResponse.current.precipMm + " mm")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal(
                        "Local Time", weatherResponse.location!!.localtime!!.split(" ")[1]
                    )
                    WeatherKeyVal("Local Date", weatherResponse.location.localtime!!.split(" ")[0])
                }
            }
        }
    }
}

@Composable
fun WeatherKeyVal(key: String, value: String) {
    Column(
        modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = key, fontWeight = FontWeight.SemiBold, color = Color.Gray)
    }
}