package com.onedtwod.illuwa.util.map

import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.onedtwod.illuwa.model.dto.Location

interface ApiMap<M,MO> {
    fun createMarker(option: MO):Marker?
    fun createMarker(optionList: List<MO>):List<Marker>?
    fun moveCamera(bound: LatLngBounds)
    fun moveCamera(location: Location)
    fun moveCamera(location: Location, zoom:Float)
    fun removeMarker(marker: M)
    fun removeMarker(markerList: List<M>)
    fun clearMap()
    fun resetMap()
    fun checkReady():Boolean
}