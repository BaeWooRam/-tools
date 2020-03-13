package com.onedtwod.illuwa.util.map

import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.onedtwod.illuwa.model.dto.Location

class GoogleMapHelper:ApiMap<Marker,MarkerOptions> {
    private val BASIC_ZOOM = 15f
    private val BASIC_BOUND_PADDING = 80
    var googleMap: GoogleMap? = null

    override fun moveCamera(location: Location) {
        googleMap!!.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(
                    LatLng(location.latitude,location.longitude),
                    BASIC_ZOOM))
    }

    override fun moveCamera(bound: LatLngBounds){
        googleMap!!.moveCamera(CameraUpdateFactory.newLatLngBounds(bound, BASIC_BOUND_PADDING))
    }

    override fun moveCamera(location: Location, zoom: Float) {
        googleMap!!.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(
                    LatLng(location.latitude,location.longitude),
                    zoom))
    }

    override fun createMarker(option: MarkerOptions):Marker{
        return googleMap!!.addMarker(option)
    }

    override fun createMarker(optionList: List<MarkerOptions>):List<Marker>{
        var markerList: ArrayList<Marker> = ArrayList()

        for(option in optionList){
            markerList.add(googleMap!!.addMarker(option))
        }

        return markerList
    }

    override fun removeMarker(marker: Marker) {
        marker.remove()
    }

    override fun removeMarker(markerList: List<Marker>) {
        for(marker in markerList){
            marker.remove()
        }
    }

    override fun clearMap() {
        googleMap!!.clear()
    }

    override fun resetMap() {
        googleMap!!.clear()
    }

    override fun checkReady():Boolean{
        if (googleMap == null) {
            Log.e("GoogleMapHelper","GoogleMap is Null")
            return false
        }
        return true
    }
}