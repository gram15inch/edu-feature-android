package com.nuhlp.googlemapapi.ui.test

import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.nuhlp.googlemapapi.databinding.FragmentMapBinding
import com.nuhlp.googlemapapi.network.model.place.Place
import com.nuhlp.googlemapapi.ui.maps.MapsActivity
import com.nuhlp.googlemapapi.util.map.BaseMapFragment
import com.nuhlp.googlemapapi.util.map.MapUtil

@BindingAdapter("bindMap")
fun bindMap(view: FragmentContainerView, mapUtil: MapUtil) {
    view.getFragment<SupportMapFragment>().getMapAsync(mapUtil)
}

@BindingAdapter("livePlace","lifecycle")
fun bindPlace(view : TextView, live:LiveData<Place>, lifecycleOwner: LifecycleOwner) {
    live.observe(lifecycleOwner){
            place -> view.text = place?.toString()?:"null~~~~~~~~~~~~!"
    }
}