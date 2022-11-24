package com.mateusz.workoutcustomer.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
class WeatherViewModel : ViewModel() {
    val LiveDataCurrent = MutableLiveData<WeatherModel>()
    val LiveDataList = MutableLiveData<String>()
}