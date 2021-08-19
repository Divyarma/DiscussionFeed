package com.example.test.ApiInterfaces

import com.example.test.models.post.DiscussionWallNewPostsResponse
import com.example.test.models.post.post_create
import retrofit2.Call
import retrofit2.http.*

interface PostNewQuesInterface{
    @POST("discussionWall/post")
    fun postques(@Header("Authorization") auto:String, @Body Post: post_create, @Query("userId") id:Int,) : Call<DiscussionWallNewPostsResponse>
}