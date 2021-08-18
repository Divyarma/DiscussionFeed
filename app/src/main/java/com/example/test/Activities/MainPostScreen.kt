package com.example.test.Activities

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.Adapters.PostsAdapter
import com.example.test.ApiInterfaces.GetPostListInterface
import com.example.test.ApiInterfaces.LoginInterface
import com.example.test.ApiInterfaces.PostNewQuesInterface
import com.example.test.Constants
import com.example.test.R
import com.example.test.models.*
import kotlinx.android.synthetic.main.activity_main_post_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MainPostScreen : AppCompatActivity() {

    var pd: Dialog? = null

    private var saveImageToInternalStorage: Uri? = null
    var QuestionImage:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_post_screen)

        Log.i("############", "Entered discussion wall")

        setSupportActionBar(toolbar_post_page)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Discussion Wall"

        toolbar_post_page.setNavigationOnClickListener {
            onBackPressed()
        }
        showpd()
        getPosts()

    }



    /**
     * A method is used  asking the permission for camera and storage and image capturing and selection from Camera.
     */

    private fun send(postCreate: post_create) {
        val service: PostNewQuesInterface = Constants.retrofit.create<PostNewQuesInterface>(PostNewQuesInterface::class.java)
        val listCall: Call<DiscussionWallNewPostsResponse> = service.postques(Constants.AccessToken,postCreate,Constants.UserId)

        listCall.enqueue(object : Callback<DiscussionWallNewPostsResponse> {
            override fun onResponse(call: Call<DiscussionWallNewPostsResponse>, response: Response<DiscussionWallNewPostsResponse>) {

                // Check weather the response is success or not.
                if (response.isSuccessful) {
                    dismisspd()
                    /** The de-serialized response body of a successful response. */
                    val response_posts: DiscussionWallNewPostsResponse? = response.body()
                    Log.i("Response Result", "$response_posts")

                } else {
                    dismisspd()
                    Toast.makeText(
                        this@MainPostScreen,
                        " Unsuccesful in adding post + ${response.message()} ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<DiscussionWallNewPostsResponse>, t: Throwable) {

                dismisspd()
                Log.i("response", "failed ")

            }
        })
    }

    fun getPosts(){
            val service: GetPostListInterface = Constants.retrofit.create<GetPostListInterface>(GetPostListInterface::class.java)
            val listCall: Call<DiscussionWallPosts> = service.postList(Constants.AccessToken,Constants.UserId)

            listCall.enqueue(object : Callback<DiscussionWallPosts> {
                override fun onResponse(call: Call<DiscussionWallPosts>, response: Response<DiscussionWallPosts>) {

                    // Check weather the response is success or not.
                    if (response.isSuccessful) {
                        dismisspd()
                        /** The de-serialized response body of a successful response. */
                        val response_posts: DiscussionWallPosts? = response.body()
                        Log.i("Response Result", "$response_posts")
                        displayPosts(response_posts!!.Result.data)

                    } else {
                        dismisspd()
                        Toast.makeText(
                            this@MainPostScreen,
                            " Unsuccesful in loading posts + ${response.message()} ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<DiscussionWallPosts>, t: Throwable) {

                    dismisspd()
                    Log.i("response", "failed ")

                }
            })
        }

    private fun displayPosts(data: List<post_data>) {

        rv_post_list.layoutManager = LinearLayoutManager(this)
        rv_post_list.setHasFixedSize(true)

        val placesAdapter = PostsAdapter(this, data as ArrayList<post_data>)
        rv_post_list.adapter = placesAdapter
        placesAdapter.setOnClickListener(object :
            PostsAdapter.OnClickListener {
            override fun onClick(position: Int, model: post_data) {

            }
        })



    }

    private fun showpd() {
        pd = Dialog(this)
        pd!!.setContentView(R.layout.dialog_custom_progress)
        pd!!.show()
    }
    private fun dismisspd(){
        if (pd != null) {
            pd!!.dismiss()
        }
    }

}