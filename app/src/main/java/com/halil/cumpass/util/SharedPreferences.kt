package com.halil.cumpass.util

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.halil.cumpass.model.ThemesModel
import com.google.gson.Gson


class SharedPreferences {
val LOCATION="LOCATION"
    companion object{
        private var sharedPreferences:android.content.SharedPreferences?=null
        @Volatile private var instance:SharedPreferences?=null
        private var lock=Any()
          operator fun invoke(context: Context)= instance?: synchronized(lock) {
                instance ?: createDatabase(context).also {
                    instance=it
                }
          }

        private fun createDatabase(context: Context):SharedPreferences{
             sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context)
            return SharedPreferences()

        }


    }

    fun themeKaydet(themesModel: ThemesModel){
                                //commit = true
        sharedPreferences!!.edit(commit = true){
            putString("themes",Gson().toJson(themesModel))
        }
    }

    fun themeAl():ThemesModel{
        return Gson().fromJson(sharedPreferences?.getString("themes",null)?:Gson().toJson(ThemesModel(null,null,"",null)),ThemesModel::class.java)
    }

    fun saveLocation(location: Location){
        sharedPreferences!!.edit(commit = true){
            putString(LOCATION,"${location.latitude} ?? ${location.longitude} ?? ${location.altitude}")


        }
        Log.e("SharedPreferences",(sharedPreferences!!.getString(LOCATION,"")?:"null" ))



    }
    fun getLocation(): Location? {

        var str=sharedPreferences!!.getString(LOCATION,null)
        var location = Location("gps")
        str?.let {str->
            var latlng = str.split(" ?? ", ignoreCase = true, limit = 5)
            latlng.let {

                location.latitude = it[0].toDouble()
                location.longitude = it[1].toDouble()
                location.altitude = it[2].toDouble()

                Log.e("SharedPreferences", latlng[0].toDouble().toString() ?: "null")
            }
        return location
        }


return null
    }

    fun deleteLocation(){
        sharedPreferences!!.edit(commit = true){
            remove(LOCATION)
        }
    }





}