package com.example.test.ApiInterfaces

import com.example.test.models.Delete_Upvote_PostData
import com.example.test.models.Delete_Upvote_PostResponse
import retrofit2.Call
import retrofit2.http.*

interface UpvotePostInterface{
    @POST("discussionWall/postupvote")
    fun upvotePost(@Header("Authorization") auto:String, @Body pid:Delete_Upvote_PostData, @Query("userId") id:Int) : Call<Delete_Upvote_PostResponse>
}