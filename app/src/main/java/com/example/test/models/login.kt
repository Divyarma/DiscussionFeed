package com.example.test.models

import java.io.Serializable

data class login(
    val ResponseCode : Int,
    val ResponseMessage : String,
    val Comments : String,
    val Result :  res_data
) : Serializable


