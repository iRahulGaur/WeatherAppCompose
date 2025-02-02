package com.rahulgaur.weatherapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.unit.dp
import com.rahulgaur.weatherapp.api.NetworkResponse

@Composable
fun WeatherPage(modifier: Modifier = Modifier, weatherViewModel: WeatherViewModel) {

    var city by remember {
        mutableStateOf("")
    }

    val weatherResult = weatherViewModel.weatherResult.observeAsState()

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
            OutlinedTextField(value = city, onValueChange = { it ->
                city = it
            }, label = {
                Text(text = "Search for your location")
            }, trailingIcon = {
                IconButton(onClick = {
                    weatherViewModel.getData(city)
                }) {
                    Icon(
                        imageVector = Icons.Default.Search, contentDescription = "Search icon"
                    )
                }
            }, modifier = Modifier.weight(1.0f), singleLine = true
            )
        } //end of search filed row

        when (val result = weatherResult.value) {
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }

            is NetworkResponse.Loading -> {
//                Text(text = if (result.isLoading) "Loading" else "Not Loading")
                CircularProgressIndicator()
            }

            is NetworkResponse.Success<*> -> {
                Text(text = result.data.toString())
            }

            null -> {
                Text(text = "unknown error!")
            }
        }
    }

}