package com.halil.cumpass.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.halil.cumpass.R
import com.halil.cumpass.model.DetailsModel
import kotlinx.android.synthetic.main.details_recycler_row.view.*

class recyclerViewDetailsAdapter(var list:ArrayList<DetailsModel>):RecyclerView.Adapter<recyclerViewDetailsAdapter.viewHolder>() {

    class viewHolder(it:View):RecyclerView.ViewHolder(it)
    //private lateinit var view:DetailsRecyclerRowBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val inflater=LayoutInflater.from(parent.context)
       // view=DataBindingUtil.inflate<DetailsRecyclerRowBinding>(inflater,R.layout.details_recycler_row ,parent,false)
        return viewHolder(inflater.inflate(R.layout.details_recycler_row,parent,false))

    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

       // view.detailsModel=DetailsModel("","")
        //view.detailsModel=list.get(position)
        holder.itemView.detailsName.text=list[position].detailsName
        holder.itemView.detailsValue.text=list[position].detailsValue

         //   Log.e("details","${list[position].detailsValue}  ${list[position].detailsName}")


    }

    override fun getItemCount(): Int {
       return list.size
    }

    public fun datasetChange(newList:ArrayList<DetailsModel>){
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
        println("datasetChangeRecyclerViewDetails")



    }

}