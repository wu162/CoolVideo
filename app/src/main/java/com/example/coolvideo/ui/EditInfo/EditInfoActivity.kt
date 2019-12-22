package com.example.coolvideo.ui.EditInfo

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.coolvideo.R
import com.example.coolvideo.data.network.CoolVideoNetwork
import com.example.coolvideo.databinding.ActivityEditinfoBinding
import com.qmuiteam.qmui.widget.QMUIRadiusImageView
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import java.io.File

class EditInfoActivity : AppCompatActivity() {
    private lateinit var editInfoViewModel: EditInfoViewModel
    private lateinit var binding : ActivityEditinfoBinding
    private lateinit var avatarPath:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_editinfo)

        editInfoViewModel =
            ViewModelProviders.of(this).get(EditInfoViewModel::class.java)

        avatarPath=""
        initChangeUserImg()
        initInfoList()
        initSubmitListener()
    }

    private fun initSubmitListener() {
        binding.infoSubmit.setOnClickListener {
            editInfoViewModel.launch{
                CoolVideoNetwork.getInstance().uploadAvatar(avatarPath)
            }
        }
    }

    private fun initChangeUserImg() {
        val userImg=findViewById<QMUIRadiusImageView>(R.id.meedit_user_img)
        userImg.setOnClickListener {
            Log.i("img","click")
            var rxPermissions = RxPermissions(this)
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(
                    { aBoolean: Boolean ->
                        if (aBoolean) {
                            ChooseAvatar()
                        } else {
                            Toast.makeText(
                                this,
                                R.string.permission_request_denied,
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    },
                    { obj: Throwable -> obj.printStackTrace() }
                )

//            val items=arrayOf("更改头像")
//            QMUIDialog.MenuDialogBuilder(this)
//                .addItems(items) { dialog: DialogInterface, which: Int ->
//                    Toast.makeText(this,"你选择了 " + items[which], Toast.LENGTH_SHORT).show()
//                    dialog.dismiss()
//                }
//                .create(mCurrentDialogStyle).show()

        }
    }

    private fun ChooseAvatar() {
        Matisse.from(this)
            .choose(MimeType.ofAll())
            .countable(true)
            .maxSelectable(1)
            .gridExpectedSize(
                getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            .thumbnailScale(0.85f)
            .imageEngine(GlideEngine())
            .forResult(REQUEST_CODE_CHOOSE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) { // 操作成功了
            when (requestCode) {
                REQUEST_CODE_CHOOSE -> {
                    val imglisturi = Matisse.obtainPathResult(data)
                    Log.i("onActivityResult",imglisturi[0].toString())
                    avatarPath=imglisturi[0].toString()
                    Glide.with(this).load(imglisturi[0]).into(binding.meeditUserImg)
                }
            }
        }
    }

    private fun getRealPath(uri: Uri): String {
        var path = uri.path
        var split = path!!.split("/")

        var filePath = getMatissePhotoPath() + File.separatorChar + split[split.size - 1]
        return filePath
    }

    private fun getMatissePhotoPath(): String {
        var file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        return file.absolutePath
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

    companion object{
        private const val REQUEST_CODE_CHOOSE = 23
    }
}