package com.example.test.models

data class post_data (
    var postId:Int,
    var userId:Int,
    var userName:String,
    var text:String,
    var image:String,
    var createdOn:String,
    var updatedOn:String,
    var studentClass:String,
    var studentBoard:String,
    var subject:String,
    var answerCount:Int,
    var upvoteCount:Int,
    var upvoted:Boolean,
    var reportCount:Int,
    var reported:Boolean
        )

