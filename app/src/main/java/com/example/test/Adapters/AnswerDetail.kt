package com.example.test.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.ApiInterfaces.UpvoteAnswerInterface
import com.example.test.Constants
import com.example.test.R
import com.example.test.models.*
import com.example.test.models.answer.upvoteAnswerdata
import kotlinx.android.synthetic.main.answeritem.view.*
import kotlinx.android.synthetic.main.post_display.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AnswerDetail(
    private val context: Context,
    private var list: ArrayList<AnswerResponseData>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostsAdapter.MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.answeritem,
                parent,
                false
            )
        )
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var model = list[position]

        if (holder is AnswerDetail.MyViewHolder) {
            holder.itemView.tv_userName.text=model.userName
            holder.itemView.tv_answer.text=model.text
            holder.itemView.iv_Answerimg.setImageURI(model.image)
            if(model.upvoted){
                holder.itemView.iv_upvote_answer.setImageResource(R.drawable.ic_heart_liked)
            }else{
                holder.itemView.iv_upvote_answer.setImageResource(R.drawable.ic_heart)
            }
            holder.itemView.btn_like.setOnClickListener {
                Log.i("Progress Bar","like button clicked")
                if (! model.upvoted) {
                    likeCall(model.answerId,holder)
                }

            }
        }
    }

    fun likeCall(id: Int, holder:AnswerDetail.MyViewHolder) {
        Log.i("###############","** Hereee in like call")
        var b:Boolean=false
        val service: UpvoteAnswerInterface = Constants.retrofit.create(UpvoteAnswerInterface::class.java)
        val l: Call<Delete_Upvote_PostResponse> = service.upvoteAns(
            Constants.AccessToken,
            upvoteAnswerdata(id), Constants.UserId)
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

    override fun getItemCount(): Int {
        return list.size
    }
}