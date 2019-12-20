package com.example.coolvideo.ui.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coolvideo.R
import com.example.coolvideo.data.model.History
import com.example.coolvideo.data.model.Video
import com.example.coolvideo.databinding.HistoryItemBinding

class HistoryListAdapter : RecyclerView.Adapter<HistoryListAdapter.ViewHolder>(),HistoryItemListener{
    private var context: Context? = null
    private lateinit var binding: HistoryItemBinding
    var historyList =  listOf<History>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val binding: HistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (context == null) {
            context = parent.context
        }
        val layoutInflater= LayoutInflater.from(context);
        binding= DataBindingUtil.inflate(layoutInflater, R.layout.history_item,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = historyList[position]
        binding.history=history
        binding.clickListener=this
        binding.videoName.text = history.videoName
        binding.userLastSeen.text = history.userLastSeen
        Glide.with(context!!).load(baseUrl+history.videoImgUrl).into(binding.videoImg)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    override fun onItemClick(history: History) {
        Toast.makeText(context, "${history.id}", Toast.LENGTH_SHORT).show()
    }

    companion object{
        private val baseUrl="http://47.100.37.242:8080/images/"
    }
}