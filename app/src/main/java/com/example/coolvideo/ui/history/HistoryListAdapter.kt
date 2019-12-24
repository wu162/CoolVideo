package com.example.coolvideo.ui.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coolvideo.R
import com.example.coolvideo.data.model.History
import com.example.coolvideo.databinding.HistoryItemBinding
import com.example.coolvideo.utils.DateUtils

class HistoryListAdapter(historyViewModel: HistoryViewModel) : RecyclerView.Adapter<HistoryListAdapter.ViewHolder>(){
    private var context: Context? = null
    private lateinit var binding: HistoryItemBinding
    var historyList =  listOf<History>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private val _historyViewModel=historyViewModel

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
        binding.clickListener=_historyViewModel
        binding.videoName.text = history.videoName
        binding.userLastSeen.text = DateUtils.formatHistoryTime(history.userLastSeen)
        Glide.with(context!!).load(baseUrl+history.videoImgUrl).into(binding.videoImg)
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

//    override fun onItemClick(history: History) {
//        Toast.makeText(context, "${history.userId}", Toast.LENGTH_SHORT).show()
//    }

    companion object{
        private val baseUrl="http://47.100.37.242:8080/images/"
    }
}