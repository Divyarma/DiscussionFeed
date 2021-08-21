package com.example.test.models

import android.net.Uri

data class AnswerPostData(
    var postId:Int,
    var text:String,
    var image:String?
)

data class AnswerResponseData(
    var answerId:Int,
    var userId:Int,
    var userName:String,
    var postId:Int,
    var text:String,
    var image:Uri?,
    var createdOn:String,
    var updatedOn:String,
    var replyCount:Int,
    var upvoteCount:Int,
    var upvoted:Boolean,
    var reportCount:Int,
    var reported:Boolean
)

