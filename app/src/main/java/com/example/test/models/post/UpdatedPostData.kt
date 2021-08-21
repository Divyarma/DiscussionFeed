package com.example.test.models.post

import android.net.Uri

data class UpdatedPostData(
    var text:String,
    var Image:Uri?,
    var postId:Int
)
