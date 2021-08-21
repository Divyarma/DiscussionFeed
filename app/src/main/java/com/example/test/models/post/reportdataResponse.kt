package com.example.test.models

data class reportdataResponse(
    var ResponseCode:Int,
    var ResponseMessage:String,
    var Comments:String,
    var result:report_data
)

data class report_data (
    var reportId:Int,
    var missingOption:Boolean,
    var missingImage:Boolean,
    var spellingMistake:Boolean,
    var incorrectAnswer:Boolean,
    var incorrectQuestion:Boolean,

    )
