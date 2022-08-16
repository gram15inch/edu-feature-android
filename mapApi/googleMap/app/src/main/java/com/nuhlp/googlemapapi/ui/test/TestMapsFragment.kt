package com.nuhlp.googlemapapi.ui.test

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.nuhlp.googlemapapi.R
import com.nuhlp.googlemapapi.databinding.FragmentMapBinding
import com.nuhlp.googlemapapi.network.model.place.Place
import com.nuhlp.googlemapapi.util.baseBinding.BaseDataBindingFragment
import com.nuhlp.googlemapapi.util.map.BaseMapFragment

class TestMapsFragment : BaseMapFragment<FragmentMapBinding>() {

    override var layoutResourceId = R.layout.fragment_map
    override val markerResourceId = R.drawable.ic_hospital_marker

    private val _testMapViewModel : TestMapViewModel by lazy {
        ViewModelProvider(this,
            TestMapViewModel.Factory(activity?.application?:
            throw IllegalAccessException("no exist activity"))
        ).get(TestMapViewModel::class.java)
    }

    override fun onCreateViewAfterMap()  {
        binding.viewModel = _testMapViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mapUtil = this

        _testMapViewModel.myLocation.observe(this){myLatLng->
            _testMapViewModel.updatePlaces(myLatLng)
        }
        _testMapViewModel.places.observe(this){places->
            Log.d("test","call places")
            _testMapViewModel.place.value = places.minByOrNull { place-> place.distance }
            places.forEach {place->
                setPlaceMarker(place,this)
            }
        }
    }

    override fun onUpdateMyLatLng(latLng: LatLng) {
        _testMapViewModel.updateMyLocation(latLng)
    }
    override fun onMarkerClick(marker: Marker): Boolean {
        val tagPlace = marker.tag as Place
        _testMapViewModel.place.value = tagPlace
        return false
    }




}