package com.example.coolvideo.ui.home

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coolvideo.R
import com.example.coolvideo.data.model.Video
import com.example.coolvideo.databinding.VideoItemBinding
import com.example.coolvideo.ui.Video.VideoActivity

class VideoListAdapter(homeViewModel: HomeViewModel) : RecyclerView.Adapter<VideoListAdapter.ViewHolder>(){
    private var context: Context? = null
    private lateinit var binding: VideoItemBinding
    var videoList =  listOf<Video>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private val _homeViewModel=homeViewModel

    class ViewHolder(val binding: VideoItemBinding) : RecyclerView.ViewHolder(binding.root) {
//        var videoCover: ImageView
//        var videoName: TextView
//
//        init {
//            videoCover = view.findViewById(R.id.video_img)
//            videoName = view.findViewById(R.id.video_name)
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (context == null) {
            context = parent.context
        }
//        val view = LayoutInflater.from(context!!).inflate(R.layout.video_item, parent, false)
//        return ViewHolder(view)
        val layoutInflater=LayoutInflater.from(context);
        binding= DataBindingUtil.inflate(layoutInflater,R.layout.video_item,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = videoList[position]
        binding.video=video
        binding.clickListener=_homeViewModel
        binding.videoName.text = video.videoName
        Glide.with(context!!).load(baseUrl+imagePath+video.videoImgUrl).into(binding.videoImg)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    companion object{
        private const val baseUrl="http://47.100.37.242:8080"
        private const val imagePath="/images/"
    }
}