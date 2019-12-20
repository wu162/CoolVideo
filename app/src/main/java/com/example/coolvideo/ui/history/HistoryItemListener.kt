package com.example.coolvideo.ui.history

import com.example.coolvideo.data.model.History

interface HistoryItemListener {
    fun onItemClick(history: History)
}