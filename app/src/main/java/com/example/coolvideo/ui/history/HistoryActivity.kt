package com.example.coolvideo.ui.history

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coolvideo.R
import com.example.coolvideo.data.Repository.HistoryRepository
import com.example.coolvideo.data.database.CoolVideoDatabase
import com.example.coolvideo.data.network.CoolVideoNetwork
import com.example.coolvideo.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityHistoryBinding= DataBindingUtil.setContentView(this,R.layout.activity_history)
        val adapter=HistoryListAdapter()
        binding.historyList.adapter=adapter
        binding.historyList.layoutManager=LinearLayoutManager(this)

        historyViewModel= ViewModelProviders.of(this, HistoryModelFactory(
            HistoryRepository.getInstance(
                CoolVideoDatabase.getHistoryDao(),
                CoolVideoNetwork.getInstance()))).get(HistoryViewModel::class.java)

        historyViewModel.dataChanged.observe(this, Observer { dataChanged ->
            // Update the cached copy of the words in the adapter.
            adapter.historyList=historyViewModel.historys
        })
        historyViewModel.getHistory()

    }
}
