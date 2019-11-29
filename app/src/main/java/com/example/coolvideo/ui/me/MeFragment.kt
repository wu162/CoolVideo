package com.example.coolvideo.ui.me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.coolvideo.R
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView

class MeFragment : Fragment() {

    private lateinit var meViewModel: MeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        meViewModel =
            ViewModelProviders.of(this).get(MeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_me, container, false)

        initGroupList(root)
        return root
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
            Toast.makeText(activity,"click",Toast.LENGTH_SHORT).show()
        }

        QMUIGroupListView.newSection(context)
            .addItemView(history,onClickListener)
            .addItemView(collection,onClickListener)
            .addTo(meList)
    }
}