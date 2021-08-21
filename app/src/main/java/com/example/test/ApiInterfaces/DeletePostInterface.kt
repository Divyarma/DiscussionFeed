package com.example.test.ApiInterfaces

import com.example.test.models.Delete_Upvote_PostData
import com.example.test.models.Delete_Upvote_PostResponse
import com.example.test.models.DiscussionWallPosts
import retrofit2.Call
import retrofit2.http.*

interface DeletePostInterface{
    @DELETE("discussionWall/post")
    fun del(@Header("Authorization") auto:String ,@Body i:Delete_Upvote_PostData, @Query("userId") id:Int) : Call<Delete_Upvote_PostResponse>
}