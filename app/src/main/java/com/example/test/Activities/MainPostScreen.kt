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
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.Adapters.PostsAdapter
import com.example.test.ApiInterfaces.GetPostListInterface
import com.example.test.ApiInterfaces.PostNewQuesInterface
import com.example.test.Constants
import com.example.test.R
import com.example.test.models.*
import com.example.test.models.post.DiscussionWallNewPostsResponse
import com.example.test.models.post.post_create
import com.example.test.models.post.post_data
import kotlinx.android.synthetic.main.activity_main_post_screen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPostScreen : AppCompatActivity() {

    var pd: Dialog? = null
    private var quesImageUri :Uri?=null
    companion object{
        const val READ_STORAGE_CODE=1
        const val PICK_IMAGE_CODE=2
    }
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

        if(intent.hasExtra("extra")){
            if(intent.getIntExtra("extra",-1)==0){
                Toast.makeText(this,"Deleted Successsfully",Toast.LENGTH_SHORT).show()}
            else if(intent.getIntExtra("extra",-1)==1){
                Toast.makeText(this,"Updated Successsfully",Toast.LENGTH_SHORT).show()
            }
        }

        showpd()
        getPosts()
        btn_AddImage_newPost.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
                showImageChooser()
            }else{
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    READ_STORAGE_CODE)
            }
        }
        btn_Submit_newPost.setOnClickListener {
            send(post_create(et_newquestion.text.toString(),quesImageUri,et_newquestion_subjectid.text.toString().toInt()))
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode== READ_STORAGE_CODE){
            if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                showImageChooser()
            }
        }
    }
    private fun showImageChooser() {
        var galleryIntent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, PICK_IMAGE_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode==Activity.RESULT_OK && requestCode== PICK_IMAGE_CODE && data!!.data !=null){
            quesImageUri=data.data
            iv_newQuestion.visibility=View.VISIBLE
            iv_newQuestion.setImageURI(quesImageUri)
        }
    }
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
                    Toast.makeText(this@MainPostScreen,"Successfully created new post",Toast.LENGTH_SHORT).show()
                    getPosts()

                } else {
                    dismisspd()
                    Toast.makeText(
                        this@MainPostScreen,
                        " Unsuccesful in adding post + ${response.message()} ",
                        Toast.LENGTH_SHORT
                    ).show()
                    getPosts()
                }
            }

            override fun onFailure(call: Call<DiscussionWallNewPostsResponse>, t: Throwable) {

                dismisspd()
                Log.i("response", "failed ")

            }
        })
        resetview()
    }
    public fun getPosts(){
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
                    Log.i("response", "failed + ${t.message}")

                }
            })
        }
    private fun displayPosts(data: List<post_data>) {

        rv_post_list.layoutManager = LinearLayoutManager(this)
        rv_post_list.setHasFixedSize(true)

        val placesAdapter = PostsAdapter(this, data as ArrayList<post_data>)
        rv_post_list.adapter = placesAdapter
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
    fun resetview(){
        et_newquestion.text.clear()
        et_newquestion_subjectid.text.clear()
        iv_newQuestion.setImageDrawable(null)
        iv_newQuestion.visibility=View.GONE

    }

}