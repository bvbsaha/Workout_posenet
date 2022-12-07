package com.bvbsaha.fitnesskursach.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bvbsaha.fitnesskursach.weather.WeatherModel

class WeatherViewModel : ViewModel() {
    val LiveDataCurrent = MutableLiveData<WeatherModel>()
    val LiveDataList = MutableLiveData<String>()
}