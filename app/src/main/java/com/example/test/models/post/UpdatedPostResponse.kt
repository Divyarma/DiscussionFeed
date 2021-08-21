package com.example.test.models.post

import com.example.test.models.post.post_data

data class UpdatedPostResponse(
    var ResponseCode:Int,
    var ResponseMessage:String,
    var Comments:String,
    var result: post_data
)
