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
import com.example.coolvideo.R
import com.example.coolvideo.data.network.CoolVideoNetwork
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

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
                    val id=CoolVideoNetwork.getInstance().addUser(name.get()!!,password.get()!!)
                    val avatarPath=setDefaultAvatar(id)
                    CoolVideoNetwork.getInstance().uploadAvatar(avatarPath)
                    saveInfo(id,avatarPath)
                    (context as Activity).finish()
                }
//                    var pref=getSharedPreferences("userInfo", MODE_PRIVATE)
//                    Log.i("pref",pref.getString("name","").toString())
//                    Log.i("pref",pref.getString("psw","").toString())
            }
        }
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
        val cw = ContextWrapper(context);
        val directory = cw.getDir("avatar", Context.MODE_PRIVATE)
        val file = File(directory, "$id.jpg")
        if (!file.exists()) {
            Log.i("path", file.toString())
            try {
                var fos = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
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
}