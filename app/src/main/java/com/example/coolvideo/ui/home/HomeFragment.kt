package com.example.coolvideo.ui.home

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coolvideo.R
import com.example.coolvideo.data.Repository.HomeVideosRepository
import com.example.coolvideo.data.database.CoolVideoDatabase
import com.example.coolvideo.data.network.CoolVideoNetwork
import com.example.coolvideo.databinding.FragmentHomeBinding
import com.example.coolvideo.ui.login.LoginActivity

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val binding: FragmentHomeBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)
        val adapter=VideoListAdapter()
        binding.videoList.adapter=adapter
        binding.videoList.layoutManager=GridLayoutManager(activity,2)

        homeViewModel=ViewModelProviders.of(this, HomeModelFactory(HomeVideosRepository.getInstance(CoolVideoDatabase.getVideoDao(),
            CoolVideoNetwork.getInstance()))).get(HomeViewModel::class.java)

        binding.setLifecycleOwner(this)
        homeViewModel.dataChanged.observe(this, Observer { dataChanged ->
            // Update the cached copy of the words in the adapter.
            adapter.videoList=homeViewModel.videos
        })
        homeViewModel.getVideos()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var pref=activity!!.getSharedPreferences("userInfo", MODE_PRIVATE)
        if (pref.getString("name","").toString() ==""){
            var intent= Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}