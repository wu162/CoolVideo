package com.example.coolvideo.ui.favor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coolvideo.R
import com.example.coolvideo.data.repository.FavorRepository
import com.example.coolvideo.data.database.CoolVideoDatabase
import com.example.coolvideo.data.network.CoolVideoNetwork
import com.example.coolvideo.databinding.ActivityFavorBinding

class FavorActivity : AppCompatActivity() {

    private lateinit var favorViewModel: FavorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityFavorBinding= DataBindingUtil.setContentView(this,R.layout.activity_favor)

        favorViewModel= ViewModelProviders.of(this, FavorModelFactory(
            this,
            FavorRepository.getInstance(
                CoolVideoDatabase.getFavorDao(),
                CoolVideoNetwork.getInstance()))).get(FavorViewModel::class.java)

        binding.favorViewModel=favorViewModel
        val adapter=FavorListAdapter(favorViewModel)
        binding.favorList.adapter=adapter
        binding.favorList.layoutManager= LinearLayoutManager(this)

        favorViewModel.dataChanged.observe(this, Observer { dataChanged ->
            // Update the cached copy of the words in the adapter.
            adapter.favorList=favorViewModel.favors
        })
        favorViewModel.getFavor()
    }
}
