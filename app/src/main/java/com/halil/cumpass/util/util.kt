package com.halil.cumpass.util

import android.opengl.Visibility
import android.view.View
import androidx.databinding.BindingAdapter
import com.halil.cumpass.R
import com.halil.cumpass.adapter.OnClickListener
import com.halil.cumpass.model.ThemesModel


var themesList=ArrayList<ThemesModel>()

fun getthemesList():ArrayList<ThemesModel>{
    themesList.clear()
    themesList.add(ThemesModel(null,R.style.Theme_Cumpass,"Original",R.color.purple_500))
    themesList.add(ThemesModel(null, R.style.Theme_Cumpass2,"Green",R.color.green))
    themesList.add(ThemesModel(null, R.style.Theme_Cumpass3,"Blue",R.color.blue))
    themesList.add(ThemesModel(null, R.style.Theme_Cumpass4,"Teal",R.color.teal_200))
    themesList.add(ThemesModel(null, R.style.Theme_Cumpass5,"Red",R.color.red))
    themesList.add(ThemesModel(null, R.style.Theme_Cumpass6,"Purple",R.color.purple))
    themesList.add(ThemesModel(null, R.style.Theme_Cumpass7,"Orange",R.color.orange))
    themesList.add(ThemesModel(null, R.style.Theme_Cumpass8,"Black",R.color.black_light))






    return themesList
}

var listener:OnClickListener?=null

@BindingAdapter("changeVisibility")
fun changeVisibility(view: View,visibility: Boolean?){
    view.visibility=if(visibility!=false) View.VISIBLE else View.INVISIBLE
}