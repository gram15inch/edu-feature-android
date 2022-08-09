package com.nuhlp.googlemapapi.ui.maps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.nuhlp.googlemapapi.R
import com.nuhlp.googlemapapi.databinding.ActivityMapsBinding
import com.nuhlp.googlemapapi.ui.test.TestMapViewModel

// 병원 마커 구해서 넣기
// 5초마다 업데이트 하지 않기 (업데이트 버튼 추가 and 앱 실행시마다) -> 내위치기준 버튼 클릭시마다
// 마커 클릭이벤트 설정 ( 클릭시 텍스트뷰에 병원정보 넣기 [livedata])
// 가장 가까운 병원정보 얻기 todo(근처에 여려개 병원정보 있을시 해결 정책 넣기[나중에])
// mapActivity를 mapFragment로 축소
// todo 널스헬퍼 전체구조 파악 @(현재진행중)
// todo 널스헬퍼 카카오,구글맵 api 적용
// todo baseMapFragment 이식
// todo place 변수로 기존 db 데이터 조회
class MapsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapsBinding
    private val mapsViewModel: MapsViewModel by lazy {
        ViewModelProvider(this,
            MapsViewModel.Factory(this.application ?:
            throw IllegalAccessException("no exist activity"))
        ).get(MapsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_maps)
        binding.viewmodel = mapsViewModel
        binding.lifecycleOwner = this
        binding.activity = this
    }

}

/*class MapsActivity : B(aseMapActivity) {

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

        _mapsViewModel.myLocation.observe(this){myLatLng->
            _mapsViewModel.updatePlaces(myLatLng)
        }
        _mapsViewModel.places.observe(this){places->
            _mapsViewModel.place.value = places.minByOrNull { place-> place.distance }
            places.forEach {place->
                setPlaceMarker(place,this)
            }
        }
    }

    override fun onUpdateMyLatLng(latLng: LatLng) {
        _mapsViewModel.updateMyLocation(latLng)
    }
    override fun onMarkerClick(marker: Marker): Boolean {
        val tagPlace = marker.tag as Place
        _mapsViewModel.place.value = tagPlace
        return false
    }

}*/
