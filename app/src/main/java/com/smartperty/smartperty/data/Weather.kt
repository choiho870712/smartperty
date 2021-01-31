package com.smartperty.smartperty.data

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import com.smartperty.smartperty.R
import com.smartperty.smartperty.utils.GlobalVariables

enum class WeatherType {
    CLEAR, PARTLY_CLOUDY, CLOUDY, RAINY
}

data class Weather(
    var wx: String = "",
    var wx_number: Int = 0,
    var avgT: Int = 0
) {
    fun getWeatherType() : WeatherType {
        val clearNumberList = mutableListOf(1,2,21,24,25)
        val partlyCloudyNumberList = mutableListOf(3,4,5,22,26,27,29)
        val cloudyNumberList = mutableListOf(6,7,8,28)
        if (clearNumberList.contains(wx_number)) return WeatherType.CLEAR
        if (partlyCloudyNumberList.contains(wx_number)) return WeatherType.PARTLY_CLOUDY
        if (cloudyNumberList.contains(wx_number)) return WeatherType.CLOUDY
        return WeatherType.RAINY
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun getWeatherBitmap() : Bitmap {
        return when(getWeatherType()) {
            WeatherType.CLEAR -> GlobalVariables.activity.resources.getDrawable(R.drawable.weather_sunny).toBitmap()
            WeatherType.PARTLY_CLOUDY -> GlobalVariables.activity.resources.getDrawable(R.drawable.weather_cloudy_and_sunny).toBitmap()
            WeatherType.CLOUDY -> GlobalVariables.activity.resources.getDrawable(R.drawable.weather_cloudy).toBitmap()
            WeatherType.RAINY -> GlobalVariables.activity.resources.getDrawable(R.drawable.weather_rainny).toBitmap()
        }
    }
}
