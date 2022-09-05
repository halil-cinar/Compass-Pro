package com.halil.cumpass.view

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.halil.cumpass.R
import com.halil.cumpass.adapter.OnClickListener
import com.halil.cumpass.adapter.recyclerViewDetailsAdapter
import com.halil.cumpass.databinding.FragmentCumpassBinding
import com.halil.cumpass.model.DetailsModel
import com.halil.cumpass.model.Model
import com.halil.cumpass.util.SharedPreferences
import com.halil.cumpass.util.listener
import com.halil.cumpass.viewModel.CompassFragmentViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_cumpass.*
import kotlin.math.abs
import kotlin.math.roundToInt

class CompassFragment : Fragment() {
private lateinit var dataBinding: FragmentCumpassBinding
private lateinit var viewModel: CompassFragmentViewModel
private  var adapter: recyclerViewDetailsAdapter = recyclerViewDetailsAdapter(arrayListOf())
    private  var adapter2: recyclerViewDetailsAdapter = recyclerViewDetailsAdapter(arrayListOf())

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        //inflater.inflate(R.layout.fragment_cumpass,container,false)
        dataBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_cumpass,container,false)
        dataBinding.color="#000000"
        Log.e("ben","oncreateView")

        return  dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProviders.of(this).get(CompassFragmentViewModel::class.java)
        viewModel.context=requireContext()

        SharedPreferences().themeAl().also {
            if(it.name!="") {

                if(it.color!=null) {
                    println("bacground changed")
                    relativeLayout.setBackgroundResource(it.color?:R.color.black)
                    relativeLayoutTop.setBackgroundResource(it.color?:R.color.black)

                }
            } else{
                relativeLayout.setBackgroundResource(R.color.green?:R.color.black)
                relativeLayoutTop.setBackgroundResource(R.color.green?:R.color.black)

            }
        }
        //viewModel.liveData.value= CompassModel(12.0,"1 1","adbcjjwskfckıwm",1)
        var model=Model(10,"" ,"")
        Log.e("ben","onViewcreate")
        viewModel.refrashData()
        viewModel.cumpassModelLiveData.value=model
detailsRecyclerView.adapter=adapter
        detailsRecyclerView2.adapter=adapter2
        detailsRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        detailsRecyclerView2.layoutManager=LinearLayoutManager(requireContext())

        var details=ArrayList<DetailsModel>()
        details.add(DetailsModel("Longitude","Waiting"))
        details.add(DetailsModel("Latitude","Waiting"))
        details.add(DetailsModel("Altitude","Waiting"))
        details.add(DetailsModel("GpsBearing","Waiting"))
        details.add(DetailsModel("Speed","Waiting"))
        details.add(DetailsModel("Accuracy","Waiting"))
        adapter.datasetChange(details)
        observeLiveData()

        createListener()



    }

    override fun onStop() {
        super.onStop()
        Log.e("ben","onstop")
        viewModel.unregisteredListener()
    }

    override fun onStart() {
        super.onStart()
        Log.e("ben","onstart")
        viewModel.registerListener()
    }


    fun observeLiveData(){
        viewModel.cumpassModelLiveData.observe(viewLifecycleOwner, Observer {
            dataBinding.cumpassModel=it
            println("observeModel")




        })


        viewModel.detailsLiveData.observe(viewLifecycleOwner, Observer {
            it.forEach {
                Log.e("ben","${it.detailsValue}")
            }
            val a= ((it[it.size-1].detailsValue).subSequence(0,it[it.size-1].detailsValue.length-2).toString().toDoubleOrNull()?.roundToInt())
            if(a!=null) {
                dataBinding.angleOfStartLocation = if (a < 0) 360 - abs(a) else a
                dataBinding.angleOfStartLocationVisibility=true
            }
            else{
                dataBinding.angleOfStartLocationVisibility=false
            }

            adapter.datasetChange(it)
        })


        viewModel.detailsLiveData2.observe(viewLifecycleOwner, Observer {
            adapter2.datasetChange(it)
            it.forEach {
                Log.e("ben","${it.detailsValue}")
            }
        })

    }



fun createListener(){

    listener=object :OnClickListener{
        override fun click(view: View) {

            if(view.id==R.id.toolbar) {
                viewModel.currentLocation?.let {
                    SharedPreferences(requireContext()).saveLocation(it)
                    Toast.makeText(
                        requireContext(),
                        "Start Location is Saved",
                        Toast.LENGTH_SHORT
                    ).show()
                    dataBinding.angleOfStartLocationVisibility=true
                }
            }
        }

    }
}


}