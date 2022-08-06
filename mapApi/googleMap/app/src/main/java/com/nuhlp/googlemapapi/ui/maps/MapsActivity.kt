package com.nuhlp.googlemapapi.ui.maps

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.model.LatLng
import com.nuhlp.googlemapapi.R
import com.nuhlp.googlemapapi.databinding.ActivityMapsBinding
import com.nuhlp.googlemapapi.util.map.BaseMapActivity
import com.nuhlp.googlemapapi.util.Constants

//todo 병원 마커 구해서 넣기
//todo 5초마다 업데이트 하지 않기 (업데이트 버튼 추가 and 앱 실행시마다)
//todo 마커 클릭이벤트 설정 ( 클릭시 텍스트뷰에 병원정보 넣기 [livedata])
//todo 가장 가까운 병원정보 얻기 (근처에 여려개 병원정보 있을시 해결 정책 넣기)
//todo 

class MapsActivity : BaseMapActivity() {

    private lateinit var binding: ActivityMapsBinding
    override val markerResourceId = R.drawable.marker
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
        _mapsViewModel.updatePlaces(Constants.LATLNG_DONGBAEK)
        _mapsViewModel.places.observe(this){ places ->
            places.forEach {
                setMarker(LatLng(it.y.toDouble(),it.x.toDouble()))
            }
        }
    }

    override fun onUpdateMyLatLng(latLng: LatLng) {
        _mapsViewModel.updateMyLocation(latLng)
    }

}
