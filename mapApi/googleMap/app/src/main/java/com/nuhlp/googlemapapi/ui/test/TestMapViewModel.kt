package com.nuhlp.googlemapapi.ui.test

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.nuhlp.googlemapapi.network.KaKaoApi
import com.nuhlp.googlemapapi.network.model.place.Place
import kotlinx.coroutines.launch

class TestMapViewModel  (application: Application) : AndroidViewModel(application) {

    private val _places = MutableLiveData<List<Place>>()
    val places : LiveData<List<Place>> = _places
    val place  = MutableLiveData<Place>()
    private val _myLocation = MutableLiveData<LatLng>()
    val myLocation = _myLocation

    fun updatePlaces(latLng: LatLng){
        viewModelScope.launch{
            _places.value = KaKaoApi.retrofitService.getPlaces(
                "HP8",
                latLng.latitude, latLng.longitude
            ).places
        }
    }
    fun updateMyLocation(latLng: LatLng){
        _myLocation.value = latLng

    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TestMapViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TestMapViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}