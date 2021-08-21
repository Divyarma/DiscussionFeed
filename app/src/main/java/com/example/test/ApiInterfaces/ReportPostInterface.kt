package com.example.test.ApiInterfaces

import com.example.test.models.post.reportdata
import com.example.test.models.reportdataResponse
import retrofit2.Call
import retrofit2.http.*

interface ReportPostInterface{
    @POST("discussionWall/postreport")
    fun rep(@Header("Authorization") auto:String, @Body rd : reportdata, @Query("userId") id:Int) : Call<reportdataResponse>
}