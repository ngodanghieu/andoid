package com.example.splashactivity.retrofit2

import com.example.homes.model.home.ResponseData
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestClient {
    private var retrofit: Retrofit? = null
    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
//                    .baseUrl("https://ngodanghieu.herokuapp.com/")
                    .baseUrl("http://192.168.1.131:8080/")
//                    .baseUrl("http://192.168.137.237:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }

    val retrofitInstanceImage: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
//                    .baseUrl("https://ngodanghieu.herokuapp.com/")
                    .baseUrl("https://api.imgur.com/3/")
//                    .baseUrl("http://192.168.137.237:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
}