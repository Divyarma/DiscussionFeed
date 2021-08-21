package com.example.test.ApiInterfaces

import com.example.test.models.Delete_Upvote_PostResponse
import com.example.test.models.answer.upvoteAnswerdata
import retrofit2.Call
import retrofit2.http.*

interface UpvoteAnswerInterface{
    @POST("discussionWall/answerupvote")
    fun upvoteAns(@Header("Authorization") auto:String, @Body pid: upvoteAnswerdata, @Query("userId") id:Int) : Call<Delete_Upvote_PostResponse>
}