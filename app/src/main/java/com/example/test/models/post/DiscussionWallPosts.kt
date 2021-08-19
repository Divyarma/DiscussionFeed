package com.example.test.models

import com.example.test.models.post.post_data

data class DiscussionWallPosts(
    var ResponseCode:Int,
    var ResponseMessage:String,
    var Comments:String,
    var Result:res_data_post
)

data class res_data_post(
    var count:Int,
    var data:List<post_data>
)
