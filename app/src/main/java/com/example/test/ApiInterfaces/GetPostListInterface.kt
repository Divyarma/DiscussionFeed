package com.example.test.ApiInterfaces

import com.example.test.models.DiscussionWallPosts
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetPostListInterface{
    @GET("discussionWall/post")
    fun postList(@Header("Authorization") auto:String , @Query("userId") id:Int) : Call<DiscussionWallPosts>
}