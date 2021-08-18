package com.example.test

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Constants {
    public var AccessToken:String=""
    public var UserId: Int=0
    val BaseURL="https://mirrorscore-android.herokuapp.com/"
    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BaseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}