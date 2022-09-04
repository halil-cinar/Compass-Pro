package com.halil.cumpass.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.halil.cumpass.R
import com.halil.cumpass.adapter.recyclerViewThemesAdapter
import com.halil.cumpass.util.getthemesList
import kotlinx.android.synthetic.main.activity_themes.*

class ThemesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_themes)

        recyclerViewThemes.layoutManager=GridLayoutManager(this,2)
        recyclerViewThemes.adapter=recyclerViewThemesAdapter(getthemesList(),this)


    }

    override fun onPause() {
        super.onPause()
        startActivity(Intent(this,MainActivity::class.java))
    }
}