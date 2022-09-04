package com.halil.cumpass.adapter


import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.halil.cumpass.databinding.ThemesRecyclerRowBinding
import com.halil.cumpass.model.ThemesModel
import com.halil.cumpass.R
import com.halil.cumpass.util.SharedPreferences
import kotlinx.android.synthetic.main.themes_recycler_row.view.*

class recyclerViewThemesAdapter(var themesList: ArrayList<ThemesModel>,val activity: Activity):RecyclerView.Adapter<recyclerViewThemesAdapter.viewHolder>(),OnClickListener {

    class viewHolder(it: ThemesRecyclerRowBinding):RecyclerView.ViewHolder(it.root){}
   private lateinit var dataView:ThemesRecyclerRowBinding
   lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        context=parent.context
        var inflater=LayoutInflater.from(parent.context)
        dataView=DataBindingUtil.inflate<ThemesRecyclerRowBinding>(inflater,R.layout.themes_recycler_row,parent,false)
        return viewHolder(dataView)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        dataView.themesModel=themesList[position]
        dataView.listener=this
        dataView.recyclerViewThemesImageView.setBackgroundResource(themesList[position].color?:R.color.black)
    }

    override fun getItemCount(): Int {
        return themesList.size
    }

    override fun click(view: View) {
        println("tıklandı")
        if (dataView.themesModel != null) {
            println("tıklandı null degil")

            themesList.withIndex().forEach{
                if(it.value.name==view.themesName.text){
                    SharedPreferences(context).themeKaydet(it.value)
                }
            }
            //MainActivity().finish()

            activity.finish()

            //context.startActivity(Intent(context,MainActivity::class.java))
        }

    }


}