package com.example.coolvideo.ui.comment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coolvideo.R
import com.example.coolvideo.data.model.Comment
import com.example.coolvideo.databinding.CommentItemBinding
import com.example.coolvideo.utils.DateUtils

class CommentListAdapter : RecyclerView.Adapter<CommentListAdapter.ViewHolder>() {
    private var context: Context? = null
    private lateinit var binding: CommentItemBinding
    var commentList =  listOf<Comment>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val binding: CommentItemBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (context == null) {
            context = parent.context
        }
        val layoutInflater= LayoutInflater.from(context);
        binding= DataBindingUtil.inflate(layoutInflater, R.layout.comment_item,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment = commentList[position]
        binding.commentName.text = comment.userName
        binding.commentTime.text = DateUtils.formatCommentTime(comment.commentTime)
        binding.commentContent.text=comment.commentContent
        Glide.with(context!!).load(baseUrl+avatarPath+comment.userId+".jpg").into(binding.commentAvatar)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    companion object{
        private const val baseUrl="http://47.100.37.242:8080"
        private const val avatarPath="/avatar/"
    }
}