package com.example.coolvideo.ui.EditInfo

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.example.coolvideo.R
import com.qmuiteam.qmui.widget.QMUIRadiusImageView
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView
import kotlinx.android.synthetic.main.activity_editinfo.*

class EditInfoActivity : AppCompatActivity() {
    private lateinit var editInfoViewModel: EditInfoViewModel
    val mCurrentDialogStyle=com.qmuiteam.qmui.R.style.QMUI_Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editinfo)

        editInfoViewModel =
            ViewModelProviders.of(this).get(EditInfoViewModel::class.java)

        initChangeUserImg()
        initInfoList()
    }

    private fun initChangeUserImg() {
        val userImg=findViewById<QMUIRadiusImageView>(R.id.meedit_user_img)
        userImg.setOnClickListener {
            Log.i("img","click")
//            val items=arrayOf("更改头像")
//            QMUIDialog.MenuDialogBuilder(this)
//                .addItems(items) { dialog: DialogInterface, which: Int ->
//                    Toast.makeText(this,"你选择了 " + items[which], Toast.LENGTH_SHORT).show()
//                    dialog.dismiss()
//                }
//                .create(mCurrentDialogStyle).show()

        }
    }

    private fun initInfoList() {
        var meList = findViewById<QMUIGroupListView>(R.id.meedit_list)
        val history=meList.createItemView(
            ContextCompat.getDrawable(this, R.mipmap.tubiao),
            "昵称",
            null,
            QMUICommonListItemView.HORIZONTAL,
            QMUICommonListItemView.ACCESSORY_TYPE_CHEVRON)
        val onClickListener: View.OnClickListener=View.OnClickListener { v->
            Toast.makeText(this,"click",Toast.LENGTH_SHORT).show()
        }

        QMUIGroupListView.newSection(this)
            .addItemView(history,onClickListener)
            .addTo(meList)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}