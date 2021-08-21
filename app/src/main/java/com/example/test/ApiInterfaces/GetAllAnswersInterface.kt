package com.example.test.ApiInterfaces

import com.example.test.models.Delete_Upvote_PostData
import com.example.test.models.DiscussionWallPosts
import com.example.test.models.answer.post_Answer_CompleteDetail
import retrofit2.Call
import retrofit2.http.*

interface GetAllAnswersInterface{
    @POST("discussionWall/answer")
    fun AnsList(@Header("Authorization") auto:String ,@Body p : Delete_Upvote_PostData, @Query("userId") id:Int) : Call<post_Answer_CompleteDetail>
}