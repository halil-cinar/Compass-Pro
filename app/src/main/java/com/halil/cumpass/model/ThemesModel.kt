package com.halil.cumpass.model

import android.graphics.Bitmap
import androidx.annotation.ColorRes
import androidx.annotation.StyleRes

data class ThemesModel(
    var image:Bitmap?,
    @StyleRes
    var imageIdRes: Int? ,
    var name:String,
    @ColorRes
    var color:Int?
) {
    var uid:Int=0
    init {
        uid+=1
    }

}