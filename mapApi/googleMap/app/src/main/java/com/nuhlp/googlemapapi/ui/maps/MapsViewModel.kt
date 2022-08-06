package com.nuhlp.googlemapapi.ui.maps

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.nuhlp.googlemapapi.network.KaKaoApi
import com.nuhlp.googlemapapi.network.model.place.Document
import com.nuhlp.googlemapapi.util.Constants.LATLNG_DONGBAEK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapsViewModel (application: Application) : AndroidViewModel(application) {

    private val _places = MutableLiveData<List<Document>>()
    val places : LiveData<List<Document>> = _places

  /*  val places = MutableLiveData<List<Document>>().apply {
        this.observe(this@BaseMapActivity){ list ->
            list.forEach {
                setMarker(LatLng(it.y.toDouble(),it.x.toDouble()))
            }
        }
    }*/


    fun updatePlaces(latLng: LatLng){
        viewModelScope.launch{
            _places.value = KaKaoApi.retrofitService.getPlaces(
                "HP8",
                latLng.latitude, latLng.longitude
            ).documents
        }
    }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MapsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MapsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}

