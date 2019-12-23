package com.example.coolvideo.ui.me

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.coolvideo.R
import com.example.coolvideo.ui.EditInfo.EditInfoActivity
import com.example.coolvideo.ui.favor.FavorActivity
import com.example.coolvideo.ui.history.HistoryActivity
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView
import kotlinx.android.synthetic.main.fragment_me.*
import java.io.File

class MeFrament : Fragment() {

    private lateinit var meFrament : MeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        meFrament =
            ViewModelProviders.of(this).get(MeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_me, container, false)

        initSwitchToEditInfo(root)
        initGroupList(root)
        return root
    }

    private fun initSwitchToEditInfo(root: View) {
        val userInfo:ConstraintLayout=root.findViewById(R.id.userInfo)
        userInfo.setOnClickListener {
            val intent=Intent(activity,EditInfoActivity::class.java)
            activity!!.startActivity(intent)
            activity!!.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
        }
    }

    private fun initGroupList(root: View) {
        var meList: QMUIGroupListView= root.findViewById(R.id.meList)
        val history=meList.createItemView(
            ContextCompat.getDrawable(context!!, R.mipmap.tubiao),
            "观看记录",
            null,
            QMUICommonListItemView.HORIZONTAL,
            QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON)
        val collection=meList.createItemView(
            ContextCompat.getDrawable(context!!, R.mipmap.tubiao),
            "我的收藏",
            null,
            QMUICommonListItemView.HORIZONTAL,
            QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON)
        val onClickListener: View.OnClickListener=View.OnClickListener { v->
            if (v is QMUICommonListItemView){
                when(v.text){
                    "观看记录" -> {
                        val intent=Intent(activity,HistoryActivity::class.java)
                        activity!!.startActivity(intent)
                    }
                    "我的收藏" -> {
                        val intent=Intent(activity,FavorActivity::class.java)
                        activity!!.startActivity(intent)
                    }
                }
            }
        }

        QMUIGroupListView.newSection(context)
            .setLeftIconSize(50,50)     //设置左边图标大小
            .addItemView(history,onClickListener)
            .addItemView(collection,onClickListener)
            .addTo(meList)
    }

    override fun onResume() {
        super.onResume()
        getInfo()
    }

    private fun getInfo() {
        var pref=activity!!.getSharedPreferences("userInfo", MODE_PRIVATE)
        val avatarPath=pref.getString("avatarPath","").toString()
        Log.i("me",avatarPath)

        Glide.with(context!!)
            .load(File(avatarPath))
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(me_user_image)
        me_user_name.text=pref.getString("name","").toString()
    }
}