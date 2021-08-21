package com.example.test.models

data class Delete_Upvote_PostData(
    var postId:Int
)
data class Delete_Upvote_PostResponse(
    var ResponseCode:Int,
    var ResponseMessage:String,
    var Comments: String,
    var Result:String
)
