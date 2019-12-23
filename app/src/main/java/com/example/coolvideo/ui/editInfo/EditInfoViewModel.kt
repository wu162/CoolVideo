package com.example.coolvideo.ui.editInfo

import android.app.Activity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coolvideo.data.network.CoolVideoNetwork
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class EditInfoViewModel (private val context: Context) : ViewModel() {

    var newAvatarPath : String="none"

    fun onSubmit(){
        launch{
            CoolVideoNetwork.getInstance().uploadAvatar(newAvatarPath)
            updateAvatar(newAvatarPath)
            (context as Activity).finish()
        }
    }

    private fun updateAvatar(newPath: String) {
        var pref=context.getSharedPreferences("userInfo", MODE_PRIVATE)
        val oldPath=pref.getString("avatarPath","").toString()
        var oldfile= File(oldPath)
        var bitmap = BitmapFactory.decodeFile(newPath);
        oldfile.deleteOnExit()
        oldfile.createNewFile()
        try {
            var fos = FileOutputStream(oldfile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        }catch (e : java.io.IOException) {
            e.printStackTrace()
        }
    }

    fun launch(block: suspend () -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }
}