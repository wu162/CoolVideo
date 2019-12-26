package com.example.coolvideo.ui.login

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.example.coolvideo.R
import com.example.coolvideo.data.network.CoolVideoNetwork
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.*
import java.net.URL

class LoginViewModel (private val context: Context) : ViewModel() {

    private val mCurrentDialogStyle = com.qmuiteam.qmui.R.style.QMUI_Dialog

    var submitClick= MutableLiveData<Int>()
    val name=ObservableField<String>("")
    val password=ObservableField<String>("")

    fun onSubmit(){
        when {
            name.get().equals("") -> {
                showInfo("昵称不能为空")
            }
            password.get().equals("") -> {
                showInfo("密码不能为空")
            }
            else -> {
                submitClick.value = submitClick.value?.plus(1)    //防止用户多次点击注册按钮
                launch{
                    val result=CoolVideoNetwork.getInstance().addUser(name.get()!!,password.get()!!)
                    if (result=="fail"){
                        showInfo("已存在该用户名")
                    }else if (result[0]=='e'){
                        val id=result.split(",")[1]
                        Log.i("tokens",id)
                        val bitmap=downloadAvatar(id)
                        val avatarPath=saveBitmap(bitmap,id)
                        saveInfo(id,avatarPath)
                        (context as Activity).finish()
                    } else{
                        val id=result.split(",")[1]
                        val avatarPath=setDefaultAvatar(id)
                        CoolVideoNetwork.getInstance().uploadAvatar(avatarPath)
                        saveInfo(id,avatarPath)
                        (context as Activity).finish()
                    }
                }
            }
        }
    }

    suspend fun downloadAvatar(id: String)=withContext(Dispatchers.IO){
        var bitmap=Glide.with(context)
            .asBitmap()
            .load("$baseUrl$avatarPath$id.jpg")
            .into(256,256)
            .get()
        bitmap
    }

    private fun showInfo(s: String) {
        QMUIDialog.MessageDialogBuilder(context)
            .setMessage(s)
            .setTitle("提示")
            .addAction(
                "确认"
            ) { dialog, index -> dialog.dismiss() }
            .create(mCurrentDialogStyle).show()
    }

    fun saveInfo(id: String, avatarPath: String) {
        var editor=context.getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit()
        editor.putString("name",name.get())
        editor.putString("psw",password.get())
        editor.putString("id",id)
        editor.putString("avatarPath",avatarPath)
        editor.apply()
    }

    fun setDefaultAvatar(id: String): String {
        val drawable = context.resources.getDrawable(R.drawable.potrait);
        val bitmap = (drawable as BitmapDrawable).bitmap
        return saveBitmap(bitmap,id)
    }

    private fun saveBitmap(bitmap: Bitmap?, id: String): String {
        val cw = ContextWrapper(context);
        val directory = cw.getDir("avatar", Context.MODE_PRIVATE)
        val file = File(directory, "$id.jpg")
        if (!file.exists()) {
            Log.i("path", file.toString())
            try {
                var fos = FileOutputStream(file)
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
//                CoolVideoNetwork.getInstance().uploadAvatar(file.toString())
            } catch (e : java.io.IOException) {
                e.printStackTrace()
            }
        }
        return file.toString()
    }

    fun launch(block: suspend () -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    companion object{
        private const val baseUrl="http://47.100.37.242:8080"
        private const val avatarPath="/avatar/"
    }
}