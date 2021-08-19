package com.example.test.models.post

import com.example.test.models.post.post_data

data class DiscussionWallNewPostsResponse(
    var ResponseCode:Int,
    var ResponseMessage:String,
    var Comments:String,
    var Result: post_data
)
