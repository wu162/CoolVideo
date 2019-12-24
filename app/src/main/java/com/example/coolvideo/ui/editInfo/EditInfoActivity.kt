package com.example.coolvideo.ui.editInfo

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.coolvideo.R
import com.example.coolvideo.data.network.CoolVideoNetwork
import com.example.coolvideo.databinding.ActivityEditinfoBinding
import com.qmuiteam.qmui.widget.QMUIRadiusImageView
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import java.io.File
import java.io.FileOutputStream

class EditInfoActivity : AppCompatActivity() {
    private lateinit var editInfoViewModel: EditInfoViewModel
    private lateinit var binding : ActivityEditinfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_editinfo)

        editInfoViewModel =
            ViewModelProviders.of(this,EditInfoModelFactory(this)).get(EditInfoViewModel::class.java)
        binding.editInfoViewModel=editInfoViewModel

        initAvatar()
        initName()
        initChangeUserImg()
    }

    private fun initName() {
        var pref=getSharedPreferences("userInfo", MODE_PRIVATE)
        var name=pref.getString("name","").toString()
        binding.editinfoName.setText(name)
        editInfoViewModel.name.set(name)
    }

    private fun initAvatar() {
        var pref=getSharedPreferences("userInfo", MODE_PRIVATE)
        var avatarPath=pref.getString("avatarPath","").toString()
        editInfoViewModel.newAvatarPath=avatarPath
        Glide.with(this)
            .load(File(avatarPath))
            .into(binding.meeditUserImg)
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
                    editInfoViewModel.newAvatarPath=imglisturi[0].toString()
                    Glide.with(this).load(imglisturi[0]).into(binding.meeditUserImg)
                }
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    companion object{
        private const val REQUEST_CODE_CHOOSE = 23
    }
}