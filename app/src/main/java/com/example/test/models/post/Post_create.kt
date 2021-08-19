package com.example.test.models.post

import android.media.Image
import android.net.Uri

data class post_create(
    var text:String,
    var image: Uri?,
    var subjectId: Int
)
