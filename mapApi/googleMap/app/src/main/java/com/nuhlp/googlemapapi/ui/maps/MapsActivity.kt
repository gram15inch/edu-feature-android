package com.nuhlp.googlemapapi.ui.maps

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.nuhlp.googlemapapi.R
import com.nuhlp.googlemapapi.databinding.ActivityMapsBinding
import com.nuhlp.googlemapapi.util.map.BaseMapActivity

// 병원 마커 구해서 넣기
// 5초마다 업데이트 하지 않기 (업데이트 버튼 추가 and 앱 실행시마다) -> 내위치기준 버튼 클릭시마다
// todo 마커 클릭이벤트 설정 ( 클릭시 텍스트뷰에 병원정보 넣기 [livedata]) @현재진행중
// todo 가장 가까운 병원정보 얻기 (근처에 여려개 병원정보 있을시 해결 정책 넣기)
// todo

class MapsActivity : BaseMapActivity() {

    private lateinit var binding: ActivityMapsBinding
    override val markerResourceId = R.drawable.ic_hospital_marker
    override val mapFragmentId= R.id.map

    private val _mapsViewModel: MapsViewModel by lazy {
        ViewModelProvider(this,
            MapsViewModel.Factory(this.application ?:
                throw IllegalAccessException("no exist activity"))
        ).get(MapsViewModel::class.java)
    }

    override fun onCreateAfter(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this,R.layout.activity_maps)
        binding.viewmodel = _mapsViewModel
        binding.lifecycleOwner = this
        binding.activity = this
        //_mapsViewModel.updatePlaces(Constants.LATLNG_DONGBAEK)
        _mapsViewModel.myLocation.observe(this){myLatLng->
            _mapsViewModel.updatePlaces(myLatLng)
        }
        _mapsViewModel.places.observe(this){places->
            _mapsViewModel.place.value = places.minByOrNull { place-> place.distance }
            places.forEach {place->
                setPlaceMarker(place,_mapsViewModel.place)
            }
        }
    }

    override fun onUpdateMyLatLng(latLng: LatLng) {
        _mapsViewModel.updateMyLocation(latLng)
    }

}
