package com.example.test.models

import java.io.Serializable

data class login_response(
    val ResponseCode : Int,
    val ResponseMessage : String,
    val Comments : String,
    val Result :  res_data
) : Serializable

data class res_data(
    val userId : Int,
    val token : token_data
)

data class token_data(
    val refresh: String,
    val access : String
)



