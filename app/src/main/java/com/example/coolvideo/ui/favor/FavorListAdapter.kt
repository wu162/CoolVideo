package com.example.coolvideo.ui.favor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coolvideo.R
import com.example.coolvideo.data.model.Favor
import com.example.coolvideo.databinding.FavorItemBinding

class FavorListAdapter(private val favorViewModel: FavorViewModel) : RecyclerView.Adapter<FavorListAdapter.ViewHolder>() {
    private var context: Context? = null
    private lateinit var binding: FavorItemBinding
    var favorList =  listOf<Favor>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }



    class ViewHolder(val binding: FavorItemBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (context == null) {
            context = parent.context
        }
        val layoutInflater= LayoutInflater.from(context);
        binding= DataBindingUtil.inflate(layoutInflater, R.layout.favor_item,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favor = favorList[position]
        binding.favor=favor
        binding.clickListener=favorViewModel
        binding.videoName.text = favor.videoName
        Glide.with(context!!).load(baseUrl+favor.videoImgUrl).into(binding.videoImg)
    }

    override fun getItemCount(): Int {
        return favorList.size
    }

//    override fun onItemClick(favor: Favor) {
//        Toast.makeText(context, "${favor.userId}", Toast.LENGTH_SHORT).show()
//    }

    companion object{
        private val baseUrl="http://47.100.37.242:8080/images/"
    }
}