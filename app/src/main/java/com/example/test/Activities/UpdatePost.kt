package com.example.test.Activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.example.test.ApiInterfaces.DeletePostInterface
import com.example.test.ApiInterfaces.UpdatePostInterface
import com.example.test.Constants
import com.example.test.Constants.retrofit
import com.example.test.R
import com.example.test.models.Delete_Upvote_PostData
import com.example.test.models.Delete_Upvote_PostResponse
import com.example.test.models.post.UpdatedPostData
import com.example.test.models.post.UpdatedPostResponse
import com.example.test.models.post.post_data
import kotlinx.android.synthetic.main.activity_main_post_screen.*
import kotlinx.android.synthetic.main.activity_update_post.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdatePost : AppCompatActivity() {

    private var mImg: Uri?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_update_post)
        setSupportActionBar(toolbar_update_page)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Update Post"

        val data=intent.getSerializableExtra("SelectedQuestion") as post_data
        mImg=data.image.toUri()

        populateUI(data)
        btn_del.setOnClickListener {
            deletee(data.postId)
        }
        btn_done.setOnClickListener {
            update(UpdatedPostData(et_ques.text.toString(),mImg,data.postId))
        }

        btn_img.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                showImageChooser()
            }else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    MainPostScreen.READ_STORAGE_CODE
                )
            }
        }

        toolbar_update_page.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== MainPostScreen.READ_STORAGE_CODE){
            if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                showImageChooser()
            }
        }
    }
    private fun showImageChooser() {
        var galleryIntent=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, MainPostScreen.PICK_IMAGE_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK && requestCode== MainPostScreen.PICK_IMAGE_CODE && data!!.data !=null){
            mImg=data.data
            iv_ques.setImageURI(mImg)
        }
    }

    fun update(updatedPostData: UpdatedPostData){
        var service=Constants.retrofit.create(UpdatePostInterface::class.java)
        var l=service.updatePost(Constants.AccessToken,updatedPostData,Constants.UserId)
        l.enqueue(object : Callback<UpdatedPostResponse>{
            override fun onResponse(
                call: Call<UpdatedPostResponse>,
                response: Response<UpdatedPostResponse>
            ) {
                if(response.isSuccessful){
                    var j= Intent(this@UpdatePost,MainPostScreen::class.java)
                    j.putExtra("extra",1)
                    startActivity(j)
                }
                }


            override fun onFailure(call: Call<UpdatedPostResponse>, t: Throwable) {
                Log.i("#############","failed")
            }

        })
    }

    private fun deletee(i: Int) {

        var service:DeletePostInterface=Constants.retrofit.create(DeletePostInterface::class.java)
        val l=service.del(Constants.AccessToken, Delete_Upvote_PostData(i),Constants.UserId)
        l.enqueue(object : Callback<Delete_Upvote_PostResponse>{
            override fun onResponse(
                call: Call<Delete_Upvote_PostResponse>,
                response: Response<Delete_Upvote_PostResponse>
            ) {
                if(response.isSuccessful){
                    var j= Intent(this@UpdatePost,MainPostScreen::class.java)
                    j.putExtra("extra",0)
                    startActivity(j)
                }
            }

            override fun onFailure(call: Call<Delete_Upvote_PostResponse>, t: Throwable) {
                Log.i("#########","failed")
            }

        })
    }

    private fun populateUI(data: post_data) {
        iv_ques.setImageURI(data.image.toUri())
        et_ques.setText(data.text)
    }
}