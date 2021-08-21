package com.example.test.models.answer

import android.net.Uri
import com.example.test.models.AnswerResponseData

data class post_Answer_CompleteDetail(
    var ResponseCode:Int,
    var ResponseMessage:String,
    var Comments:String,
    var result:ress
)
data class ress(
    var count:Int,
    var postid:Int,
    var userId:Int,
    var userName:String,
    var text:String,
    var image: Uri?,
    var data:List<AnswerResponseData>
    )
