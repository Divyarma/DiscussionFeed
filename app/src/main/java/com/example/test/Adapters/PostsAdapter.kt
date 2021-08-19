package com.example.test.Adapters

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.test.Activities.DetailPost
import com.example.test.Activities.UpdatePost
import com.example.test.ApiInterfaces.AnswerPostInterface
import com.example.test.ApiInterfaces.ReportPostInterface
import com.example.test.ApiInterfaces.UpvotePostInterface
import com.example.test.Constants
import com.example.test.R
import com.example.test.models.*
import com.example.test.models.post.post_data
import com.example.test.models.post.reportdata
import kotlinx.android.synthetic.main.post_display.view.*
import kotlinx.android.synthetic.main.report_dialog_box.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostsAdapter(
    private val context: Context,
    private var list: ArrayList<post_data>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.post_display,
                parent,
                false
            )
        )
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var model = list[position]

        if (holder is MyViewHolder) {
            holder.itemView.tv_username_item.text = model.userName
            holder.itemView.tv_QuestionPost_item.text = model.text
            holder.itemView.tv_postClass_item.text = model.studentClass
            holder.itemView.tv_postTime_item.text = model.updatedOn
            if (model.upvoted) {
                holder.itemView.btn_like.setImageResource(R.drawable.ic_heart_liked)
            } else {
                holder.itemView.btn_like.setImageResource(R.drawable.ic_heart)
            }                      // To keep like btn's color accordingly
            holder.itemView.btn_like.setOnClickListener {
                Log.i("Progress Bar","like button clicked")
                if (! model.upvoted) {
                    likeCall(model.postId,holder)
                }

            }
            holder.itemView.btn_report.setOnClickListener {
                reportWork(model.postId)
            }
            holder.itemView.btn_answer.setOnClickListener {
                holder.itemView.ll_Answer.visibility=View.VISIBLE
            }
            holder.itemView.btn_postAns.setOnClickListener {
                Log.i("##########","answer_posting ............")
                val ans=holder.itemView.et_answer.text.toString()
                answeredQues(AnswerPostData(model.postId,ans,model.image))
                holder.itemView.et_answer.text.clear()
                holder.itemView.ll_Answer.visibility=View.GONE
            }
            holder.itemView.tv_QuestionPost_item.setOnClickListener {
                val intent = Intent(context,DetailPost::class.java)
                intent.putExtra("SelectedQuestion",model.postId)
                startActivity(context,intent,null)
            }
            holder.itemView.btn_edit.setOnClickListener {
                val intent = Intent(context,UpdatePost::class.java)
                intent.putExtra("SelectedQuestion",model)
                startActivity(context,intent,null)
            }
        }
    }

    private fun reportWork(id: Int) {
        val dialog=Dialog(context)
        dialog.setContentView(R.layout.report_dialog_box)
        dialog.report_dialog.setOnClickListener {
            var sendr= reportdata(id,retrep(dialog.rb_m_option),retrep(dialog.rb_m_image),retrep(dialog.rb_spell_mistake),retrep(dialog.rb_inc_ans),retrep(dialog.rb_inc_ques))
            reportPost(sendr)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun reportPost(s: reportdata) {
        val service:ReportPostInterface=Constants.retrofit.create(ReportPostInterface::class.java)
        val repd=service.rep(Constants.AccessToken,s,Constants.UserId)
        repd.enqueue(object : Callback<reportdataResponse> {
            override fun onResponse(call: Call<reportdataResponse>, response: Response<reportdataResponse>) {

                // Check weather the response is success or not.
                if (response.isSuccessful) {
                    /** The de-serialized response body of a successful response. */
                    val response_posts: reportdataResponse? = response.body()
                    Log.i("Response Result", "$response_posts")
                    Toast.makeText(context,"Reported Successfully",Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(
                        context,
                        " Unsuccesful in loading posts + ${response.message()} ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<reportdataResponse>, t: Throwable) {
                Log.i("response", "failed + ${t.message}")
            }
        })
            }

    fun retrep(Rb:RadioButton):String{
        if(Rb.isChecked){
            return "true"
        }else{
            return "false"
        }
    }

    fun likeCall(id: Int, holder: MyViewHolder) {
        Log.i("###############","** Hereee in like call")
        var b:Boolean=false
        val service:UpvotePostInterface=Constants.retrofit.create(UpvotePostInterface::class.java)
        val l:Call<Delete_Upvote_PostResponse> = service.upvotePost(Constants.AccessToken,
            Delete_Upvote_PostData(id),Constants.UserId)
        l.enqueue(object : Callback<Delete_Upvote_PostResponse> {
            override fun onResponse(
                call: Call<Delete_Upvote_PostResponse>,
                response: Response<Delete_Upvote_PostResponse>
            ) {
                if(response.isSuccessful){
                    holder.itemView.btn_like.setImageResource(R.drawable.ic_heart_liked)
                    Log.i("###############","** successful ${response.body()}")
                }else{
                    Log.i("###############","** ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Delete_Upvote_PostResponse>, t: Throwable) {
                Log.i("###############","Failed")
            }

        })

    }

    fun answeredQues(s:AnswerPostData){
        val service:AnswerPostInterface=Constants.retrofit.create(AnswerPostInterface::class.java)
        val l=service.ans(Constants.AccessToken,s,Constants.UserId)
        l.enqueue(object :Callback<Delete_Upvote_PostResponse>{
            override fun onResponse(
                call: Call<Delete_Upvote_PostResponse>,
                response: Response<Delete_Upvote_PostResponse>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(context,"Answer Posted",Toast.LENGTH_SHORT).show()
                }else{
                    Log.i("############","Unsuccessful + ${response.message()}")
                    Toast.makeText(context,"Answer not Posted",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Delete_Upvote_PostResponse>, t: Throwable) {
                Log.i("############","Failed")
                Toast.makeText(context,"Answer not Posted",Toast.LENGTH_SHORT).show()
            }

        })
    }
    override fun getItemCount(): Int {
        return list.size
    }
}