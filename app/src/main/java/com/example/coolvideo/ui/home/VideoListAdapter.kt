package com.example.coolvideo.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coolvideo.R
import com.example.coolvideo.data.model.Video

class VideoListAdapter : RecyclerView.Adapter<VideoListAdapter.ViewHolder>() {
    private var context: Context? = null
    var videoList =  listOf<Video>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var videoCover: ImageView
        var videoName: TextView

        init {
            videoCover = view.findViewById(R.id.video_img)
            videoName = view.findViewById(R.id.video_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (context == null) {
            context = parent.context
        }
        val view = LayoutInflater.from(context!!).inflate(R.layout.video_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val video = videoList[position]
        holder.videoName.text = video.videoName
        Glide.with(context!!).load(video.videoImgUrl).into(holder.videoCover)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }
}

class VideoListListener(val clickListener: (videoId: Int)->Unit){
    fun onClick(video:Video)=clickListener(video.id)
}