package com.example.test.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.models.post_data
import kotlinx.android.synthetic.main.post_display.view.*

class PostsAdapter(
                   private val context: Context,
                   private var list: ArrayList<post_data>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.post_display,
                parent,
                false
            )
        )
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var model = list[position]

        if (holder is MyViewHolder) {
            holder.itemView.tv_username_item.text=model.userName
            holder.itemView.tv_QuestionPost_item.text=model.text
            holder.itemView.tv_postClass_item.text=model.studentClass
            holder.itemView.tv_postTime_item.text=model.updatedOn
            if(model.upvoted){
                holder.itemView.btn_like.setImageResource(R.drawable.ic_heart_liked)
            }else{
                holder.itemView.btn_like.setImageResource(R.drawable.ic_heart)
            }
            holder.itemView.btn_like.setOnClickListener{
                if(!model.upvoted){
                    holder.itemView.btn_like.setImageResource(R.drawable.ic_heart_liked)
                    model.upvoted=true
                    model.upvoteCount++
                }else{
                    holder.itemView.btn_like.setImageResource(R.drawable.ic_heart)
                    model.upvoteCount--
                    model.upvoted=false
                }
            }
            holder.itemView.setOnClickListener {

                if (onClickListener != null) {
                    onClickListener!!.onClick(position, model)
                }
            }
        }
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }
    interface OnClickListener {
        fun onClick(position: Int, model: post_data)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}