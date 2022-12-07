package com.bvbsaha.fitnesskursach.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bvbsaha.fitnesskursach.databinding.FragmentHistoryBinding
import com.bvbsaha.fitnesskursach.weather.WeatherModel
import com.bvbsaha.fitnesskursach.weather.WeatherViewModel
import com.squareup.picasso.Picasso
import org.json.JSONObject


const val API_KEY = "7110427c6c5a455aa5d183209222111"
class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentHistoryBinding
    private val model: WeatherViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHistoryBinding.inflate(inflater,container, false)
        requestWeatherData("Minsk")
        updateCurrent()
        return binding.root
    }
    private fun updateCurrent() = with(binding){
        model.LiveDataCurrent.observe(viewLifecycleOwner){
            tvData.text = "Дата последнего обновления:\n" + it.time
            if (it.city == "Minsk"){ tvCity.text = "Минск"
            } else tvCity.text = it.city
            tvCurrentTemp.text = it.currentTemp + "°C"
           // tvCondition.text = it.condition
            Picasso.get().load("https:" + it.imageUrl).into(imageWeather)
            val zero: Int = 0
            if (tvCurrentTemp.text.toString().contains("-")) {
                tvRun.text = "Сегодня неблагоприятная погода для пробежки на улице"
            }else tvRun.text = "Сегодня подходящая погода для тренировок улице"
        }
    }


    private fun requestWeatherData(city: String){
        val url = "https://api.weatherapi.com/v1/forecast.json?key=" +
                API_KEY +
                "&q=" +
                city +
                "&days=" +
                "3" +
                "&aqi=no&alerts=no"
        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                result -> parsWeatherData(result)
            },
            {
                error -> Log.d( "MyLog", "Error: $error")
            }

        )
        queue.add(request)
    }

    private fun parsWeatherData(result: String) {
        val mainObject = JSONObject(result)
        val item = WeatherModel(
            mainObject.getJSONObject("location").getString("name"),
            mainObject.getJSONObject("current").getString("last_updated"),
            mainObject.getJSONObject("current").getJSONObject("condition").getString("text"),
            mainObject.getJSONObject("current").getString("temp_c"),
            mainObject.getJSONObject("current").getJSONObject("condition").getString("icon")

        )
        model.LiveDataCurrent.value = item
        Log.d("MyLog","city ${item.city}")
        Log.d("MyLog","time ${item.time}")
        Log.d("MyLog","Condition ${item.condition}")
        Log.d("MyLog","Temp ${item.currentTemp}")
        Log.d("MyLog","IconUrl ${item.imageUrl}")


    }


}



