package com.example.test.Activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.Adapters.AnswerDetail
import com.example.test.ApiInterfaces.GetAllAnswersInterface
import com.example.test.Constants
import com.example.test.R
import com.example.test.models.AnswerResponseData
import com.example.test.models.Delete_Upvote_PostData
import com.example.test.models.answer.post_Answer_CompleteDetail
import kotlinx.android.synthetic.main.activity_detail_post.*
import kotlinx.android.synthetic.main.activity_main_post_screen.*
import kotlinx.android.synthetic.main.activity_main_post_screen.toolbar_post_page
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPost : AppCompatActivity() {

    var pd: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_post)
        setSupportActionBar(toolbar_post_page)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = ""
        var k=intent.getIntExtra("SelectedQuestion",0)
        toolbar_post_page.setNavigationOnClickListener {
            onBackPressed()
        }
        getAnswers(k)

    }

    fun getAnswers(i:Int){
        val service: GetAllAnswersInterface = Constants.retrofit.create<GetAllAnswersInterface>(
            GetAllAnswersInterface::class.java)
        val listCall:Call<post_Answer_CompleteDetail> = service.AnsList(Constants.AccessToken,
            Delete_Upvote_PostData(i), Constants.UserId)

        listCall.enqueue(object : Callback<post_Answer_CompleteDetail> {
            override fun onResponse(call: Call<post_Answer_CompleteDetail>, response: Response<post_Answer_CompleteDetail>) {
                if (response.isSuccessful) {
                    dismisspd()
                    val response_posts:post_Answer_CompleteDetail? = response.body()
                    Log.i("Response Result", "$response_posts")
                    showAnswers(response_posts!!.result.data)

                } else {
                    dismisspd()
                    Toast.makeText(
                        this@DetailPost,
                        " Unsuccesful in loading posts + ${response.message()} ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<post_Answer_CompleteDetail>, t: Throwable) {

                dismisspd()
                Log.i("response", "failed + ${t.message}")

            }
        })
    }
    fun showAnswers(data: List<AnswerResponseData>){
        rv_answerlist.layoutManager = LinearLayoutManager(this)
        rv_answerlist.setHasFixedSize(true)

        val placesAdapter = AnswerDetail(this, data as ArrayList<AnswerResponseData>)
        rv_answerlist.adapter = placesAdapter
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