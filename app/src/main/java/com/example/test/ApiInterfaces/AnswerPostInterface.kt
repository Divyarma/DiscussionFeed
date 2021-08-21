package com.example.test.ApiInterfaces

import com.example.test.models.AnswerPostData
import com.example.test.models.Delete_Upvote_PostResponse
import com.example.test.models.DiscussionWallPosts
import retrofit2.Call
import retrofit2.http.*

interface AnswerPostInterface{
    @POST("discussionWall/answer")
    fun ans(@Header("Authorization") auto:String, @Body pa : AnswerPostData, @Query("userId") id:Int) : Call<Delete_Upvote_PostResponse>
}