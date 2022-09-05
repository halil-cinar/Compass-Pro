package com.halil.cumpass.viewModel

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.widget.Toast
import androidx.core.util.toHalf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.halil.cumpass.model.DetailsModel
import com.halil.cumpass.model.Model
import com.halil.cumpass.util.PermissionsControl
import com.halil.cumpass.util.SharedPreferences
import kotlin.math.abs
import kotlin.math.round
import kotlin.math.roundToInt

class CompassFragmentViewModel():ViewModel() {
    var context: Context?=null
    var cumpassModelLiveData=MutableLiveData<Model>()
    var detailsLiveData=MutableLiveData<ArrayList<DetailsModel>>()
    var detailsLiveData2=MutableLiveData<ArrayList<DetailsModel>>()


    private lateinit var sensorManager: SensorManager
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener


 var currentLocation:Location?=null



    fun refrashData(){

//liveData.value=Model(1.0,"","")

        if(PermissionsControl.gpsPermissions) {
            gpsLocation()
        }

    }

    @SuppressLint("MissingPermission")
    private fun gpsLocation(){
        locationManager=context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        locationListener= object :LocationListener{
            override fun onLocationChanged(p0: Location) {
                currentLocation=p0
                observeData(p0,null)
                println("knasfnknkak")
            }

        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,2f,locationListener)
        var loc=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if(( loc ?: (locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)))!=null){
            observeData(loc ?: (locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)),null)

        }

    }
   private fun observeData(loc: Location?,floatArray:FloatArray?){
       var model=cumpassModelLiveData.value


      // details=detailsLiveData.value
        println("moddel atandı")
       if(model!=null){
           println("model null degill")
           if(loc!=null){


                   var details=ArrayList<DetailsModel>()
               var longitude=loc.longitude.toString()
               longitude=longitude.substring(0,if (longitude.length<12) longitude.length else 12)

               var latitude=loc.latitude.toString()
               latitude=latitude.substring(0,if (latitude.length<12) latitude.length else 12)

               details.add(DetailsModel("Longitude",longitude+"°"))
                   details.add(DetailsModel("Latitude",latitude+"°"))
                   details.add(DetailsModel("Altitude",loc.altitude.toString()+"m"))
               details.add(DetailsModel("GpsBearing",loc.bearing.toString()+"°"))
                   details.add(DetailsModel("Speed",(loc.speed*3.6).roundToInt().toString()+"km/h"))
                   details.add(DetailsModel("Accuracy",if (loc.accuracy<1000) loc.accuracy.toString()+"m" else (loc.accuracy/1000.0).toString()+"km"))
               context?.let {context->

                   if(SharedPreferences(context).getLocation()!=null){

                       var bearing= loc.bearingTo(  SharedPreferences(context).getLocation())
                       bearing=round(bearing*10)/10

                       bearing=if(bearing<0)360-abs(bearing) else bearing
                     details.add(  DetailsModel(
                           "Angle With\nStart Location",
                         "$bearing°"
                       )
                     )
                   }else{
                       details.add(  DetailsModel(
                           "Angle With \n Start Location",
                           "Start Location\n Not Saved"
                       )
                       )
                   }





               }

               detailsLiveData.value=details



               println("loc null degil")
               model.address=addressFinder(loc)?:""
               //model.address=" "
               model.latitudeAndLongitude=" ${latitude}°  ${longitude}°"
                println(model.latitudeAndLongitude)

           }
          if (floatArray!=null){
               println("array null degil")
                model.degrees=floatArray[0].toDouble().roundToInt()

                println(model.degrees)

              var details2=ArrayList<DetailsModel>()
              details2.clear()
              details2.add(DetailsModel("Azimuth",model.degrees.toString()))
              details2.add(DetailsModel("Pitch",floatArray[1].toString()))
              details2.add(DetailsModel("Roll",floatArray[2].toString()))
              detailsLiveData2.value=details2

           }
           cumpassModelLiveData.value=model!!

       }
   }

    private fun addressFinder(loc:Location):String?{
        println("adresFinder cagrıldı")
        try {

            var geocoder=Geocoder(context!!).getFromLocation(loc.latitude,loc.longitude,1)
            println("geocoder olusturuldu")
            if (!geocoder.isNullOrEmpty()) {
                println("Geocoder null degil")

                geocoder[0].apply {
                    // subThoroughfare+" "+subAdminArea + " " + adminArea + " " + countryName
                    return getAddressLine(maxAddressLineIndex)
                }
            }

        }catch (e:Exception){
            e.printStackTrace()
            //Toast.makeText(context!!, e.localizedMessage, Toast.LENGTH_SHORT).show()

            return "Address not found"
        }
        return null
    }


    fun registerListener(){
        sensorManager=context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION).also {
           if ( !sensorManager.registerListener(
                sensorEventListener,
                it,
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_UI

                )){
               Toast.makeText(context!!, "Your device does not have some sensors. Data may be wrong", Toast.LENGTH_LONG).show()
           }
        }

    }

    fun unregisteredListener(){
        sensorManager.unregisterListener(sensorEventListener,sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION))

    }



var sensorEventListener=object :SensorEventListener{

    override fun onSensorChanged(p0: SensorEvent?) {

        if (p0?.sensor?.type==Sensor.TYPE_ORIENTATION){
            observeData(null,p0.values)
            println(p0.values)

        }

        println("sensor degişti")
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}


}