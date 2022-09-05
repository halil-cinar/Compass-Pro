package com.halil.cumpass.view

import android.Manifest
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.halil.cumpass.R
import com.halil.cumpass.util.PermissionsControl
import com.halil.cumpass.util.SharedPreferences
import com.halil.cumpass.util.listener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_cumpass.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

            //var toolBar= toolbar

       // toolBar.setNavigationIcon(R.drawable.cumpass)


@ColorRes
var colorRes:Int?=null
        val sharedPreferences=SharedPreferences(this)
        sharedPreferences.themeAl().also {
            if(it.name!=""){

                println("themes changing")
                if (it.imageIdRes!=null) {
                    setTheme(it.imageIdRes!!)
                    colorRes=it.color?:R.color.green
                    println("themes changed")
                }



                println("it  ${it.name} ${it.color} ${it.imageIdRes} ")


            }else{
                setTheme(R.style.Theme_Cumpass2)

            }

        }
        Log.e("ben","oncreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //toolbar.setTitle(R.string.app_name)

        toolbar.setNavigationIcon(R.drawable.ic_baseline_dehaze_24)
        //toolBar.inflateMenu(R.menu.menu)
        toolbar.setBackgroundResource(colorRes?:R.color.green)
        setSupportActionBar(findViewById(R.id.toolbar))
        //supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)

        //supportActionBar?.title = "selam"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_DENIED &&
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_DENIED){

            println("izin istendi")
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),1)
        }else{
            PermissionsControl.gpsPermissions=true
        }


    }

    override fun onStart() {
        super.onStart()
        Log.e("ben","onstartmain")
        theme.applyStyle(R.style.Theme_Cumpass2,true)

    }

    override fun onStop() {
        super.onStop()
        Log.e("ben","onstopmain")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if(requestCode==1&&!permissions.isNullOrEmpty()&&grantResults.isNotEmpty()){
            if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                PermissionsControl.gpsPermissions=true
                finish()
                startActivity(Intent(this,MainActivity::class.java))
            }else if(grantResults[1]== PackageManager.PERMISSION_GRANTED){
                PermissionsControl.gpsPermissions=true
                finish()
                startActivity(Intent(this,MainActivity::class.java))
            }else{
                Toast.makeText(this, "You Must Permission", Toast.LENGTH_LONG).show()
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        menuInflater.inflate(R.menu.menu,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId==R.id.themes){
            finish()
            startActivity( Intent(this,ThemesActivity::class.java))

        }
        if(item.itemId== android.R.id.home){
            if (slidingPanelLayout.closePane()) {
                slidingPanelLayout.open()
            }
            if(!slidingPanelLayout.openPane()){
                slidingPanelLayout.close()

            }

        }
        if(item.itemId==R.id.createStartDestination){


            listener?.click(this.toolbar)

        }
        if(item.itemId==R.id.delete_location){
            SharedPreferences(this).deleteLocation()
            Toast.makeText(this, "Start location is deleted", Toast.LENGTH_SHORT).show()

        }



        return super.onOptionsItemSelected(item)
    }






}