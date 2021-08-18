package com.example.test.ApiInterfaces

import com.example.test.models.login
import com.example.test.models.Login_body

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginInterface {
    @POST("login")
    fun LoginCheck(@Body loginBody: Login_body ) : Call<login>
}
