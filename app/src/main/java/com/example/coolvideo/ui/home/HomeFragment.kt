package com.example.coolvideo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coolvideo.R
import com.example.coolvideo.data.Repository.HomeVideosRepository
import com.example.coolvideo.data.database.CoolVideoDatabase

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val recyclerView= activity!!.findViewById<RecyclerView>(R.id.video_List)
        val adapter=VideoListAdapter()
        recyclerView.adapter=adapter
        recyclerView.layoutManager=GridLayoutManager(context,2)

        homeViewModel=ViewModelProviders.of(this, HomeModelFactory(HomeVideosRepository.getInstance(CoolVideoDatabase.getVideoDao()))).get(HomeViewModel::class.java)

        homeViewModel.dataChanged.observe(this, Observer { dataChanged ->
            // Update the cached copy of the words in the adapter.
            adapter.videoList=homeViewModel.videos
        })
        homeViewModel.getVideos()
    }
}