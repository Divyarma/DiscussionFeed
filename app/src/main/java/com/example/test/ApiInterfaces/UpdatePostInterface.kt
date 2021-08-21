package com.example.test.ApiInterfaces

import com.example.test.models.post.UpdatedPostData
import com.example.test.models.post.UpdatedPostResponse
import retrofit2.Call
import retrofit2.http.*

interface UpdatePostInterface{
    @PUT("discussionWall/post")
    fun updatePost(@Header("Authorization") auto:String, @Body UpdatedPost : UpdatedPostData, @Query("userId") id:Int) : Call<UpdatedPostResponse>
}