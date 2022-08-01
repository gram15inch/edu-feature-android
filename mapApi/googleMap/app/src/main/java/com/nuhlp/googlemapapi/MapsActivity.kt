package com.nuhlp.googlemapapi

import android.Manifest
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.*
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.nuhlp.googlemapapi.databinding.ActivityMapsBinding
import com.nuhlp.googlemapapi.util.PermissionPolicy
import java.util.*


/*
* todo 1
* */


/* map api 학습 일지

  1. 위치 요청 -> 권한 필요
  2. 권한 요청 -> ActivityResultLauncher 필요
  3. ARL 요청 -> ActivityResult 필요
  4. AR 요청 -> Contracts, Callback 필요
    4-1. Contracts 학습 ->  Intent 생성, 결과코드 해독 등
    4-2. Callback 학습 -> 결과처리 :: 호출 액티비티가 재시작 되서 작업순서가 사라져도 결과를 처리할 수 있게 결과처리함수를 직접주입하기 위해 사용

 ** 7/31 **
  1. 더 자세한 위치 요청 -> 여러개의 권한 필요 (자세한 위치 단독으로 요쳥 불가 사용자가 정확도를 선택 할 수 있어야함)
  2. 여러개의 권한 요청 -> AL 생성시 여러개의 권한을 담당,처리할 수 있는 Contracts , Callback 필요
    2-1. Contracts -> ActivityResultContracts.RequestMultiplePermissions()
    2-2. Callback  -> (Map<String,Boolean>) -> Unit 타입으로 만든후 권한별 승인여부 확인후 개별처리하는 로직 생성

 ** 8/1 **
  1. gps 요청(매번 권한확인코드 작성 필요) -> 통합위치정보 제공자 클라이언트 필요(FusedLocationProviderClient)
  2. 위 클라이언트로 마지막 알려진 위치 요청 -> 통제자 위치업데이트 필요
    2-1. 통제자 -> 구글 play 서비스에서 공통적으로 사용 하는 위치 API (구글 play api 를 사용하는 앱끼리 위치정보를 공유::자원절약)
  3. 위치 업데이트 요청 -> 기기에 위치설정(gps 등..) 확인 필요
  4. 위치설정 확인 요청 ->  SettingsClient, Task<LocationSettingsResponse> 필요
    4-1. SettingsClient -> 위치를 제공해주는 통제자를 앱과 연결함 todo 확인필요
    4-2. Task<LocationSettingsResponse> ->  SC.checkLocationSettings 로 생성 (파라미터에 넣을 LocationSettingsRequest, LocationRequest 필요)
        4-2-1. LocationRequest -> 통제자에게 위치를 바로 요청하지 않고 요청서(간격/정확도/전력)을 만들어 설정후 요청서를토대로 받음
            4-2-1-1. LocationRequest -> .create() 메서드로 인스턴스화 후 설정값 초기화
               * interval = 10000  (기본 업데이트 간격)
               * fastestInterval = 5000 (앱이 사용할 수 있는 가장빠른 간격)
               * priority = LocationRequest.PRIORITY_HIGH_ACCURACY (정확도/전력소비량) :: 앱의 권한 정확도와 설정의 간격들의 조합
        4-2-2. LocationSettingsRequest -> 하나이상의 LocationRequest 의 추가 필요 .addLocationRequest(locationRequest)로 추가
            4-2-2-1. .Builder() 메서드로 인스턴스화 후 추가 (후에 파라미터로 넣을때 .build() 사용)
    4-3. Task 로 결과 확인
  5. Task 로 결과처리 리스너 연결 (성공/addOnSuccessListener, 실패/addOnFailureListener)
    5-1. 성공 -> 여기부터 현재위치 접근 가능
    5-2. 실패 -> 사용자에게 위치 설정을 수정할 수 있는 권한을 요청
        5-2-1. 실패 리스너 에서 파라미터로 받은 예외에서 ResolvableApiException 타입확인
        5-2-2. 타입 확인된 예외로 startResolutionForResult 호출해 설정 수정 권한 입력받음

* */

class MapsActivity : AppCompatActivity(),
    OnMapReadyCallback,
    OnMyLocationButtonClickListener,
    OnMyLocationClickListener,
    ActivityResultCallback<Map<String,Boolean>>  {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    val LATLNG = LatLng(37.566418,126.977943)
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    fun setLocationSetting(){
        val locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        val REQUEST_CHECK_SETTINGS = 0x1
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener { locationSettingsResponse ->
            // All location settings are satisfied. The client can initialize
            // location requests here.
            withCurrentLatLng {lo-> moveCameraLauncher(mMap, lo) }
        }

        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    private fun withCurrentLatLng(callback:(LatLng)->Unit) {
        val checkP = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (checkP == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                callback(LatLng(it.latitude, it.longitude))
            }
        }
    }

    override fun onActivityResult(permissions: Map<String, Boolean>) = permissions.forEach{
        when{
            it.key == Manifest.permission.ACCESS_COARSE_LOCATION && it.value ->{PermissionPolicy.defaultGrant("ACCESS_COARSE_LOCATION")
               //val check = ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                //if(check) mMap.isMyLocationEnabled = true
            }
            it.key == Manifest.permission.ACCESS_FINE_LOCATION && it.value->{PermissionPolicy.defaultGrant("ACCESS_FINE_LOCATION")}
            else ->{PermissionPolicy.defaultReject(it.key)}
        }
    }


    fun onActivityResult(isGranted: Boolean) {
        if (isGranted)
            PermissionPolicy.defaultGrant()
        else
            PermissionPolicy.defaultReject()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val cameraPosition = CameraPosition.Builder()
            .target(LATLNG)
            .zoom(17.0f)
            .build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        mMap.moveCamera(cameraUpdate)

        val bitmapDrawable = bitmapDescriptorFromVector(this,R.drawable.marker)
        val discriptor = bitmapDrawable
        val markerOptions = MarkerOptions()
            .position(LATLNG)
            .icon(discriptor)
        markerOptions.setAddress()
        /*.title("marker in Seoul City Hall")
            .snippet("37.566418,126.977943")*/
        mMap.addMarker(markerOptions)

        val checkP= ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if(checkP == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
            mMap.setOnMyLocationButtonClickListener(this)
            mMap.setOnMyLocationClickListener(this)
        }

    }
    override fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG)
            .show()
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
            .show()
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        setLocationSetting()
        return true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkPermission()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)



        // todo 현재위치로 카메라 포지션 이동
        //moveCameraLauncher(mMap,latLng)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    fun moveCameraLauncher(map:GoogleMap,latLng: LatLng){
        val cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(17.0f)
            .build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        map.moveCamera(cameraUpdate)
    }

    val test123 = OnMapReadyCallback{

    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private  fun MarkerOptions.setAddress(){
        try {// todo 주소 클래스 분석
            val geo =
                Geocoder(this@MapsActivity, Locale.getDefault())
            val addresses: List<Address> = geo.getFromLocation(LATLNG.latitude,LATLNG.longitude, 1)
            if (addresses.isEmpty()) {
                title("Waiting for Location")
            } else {
                if (addresses.size > 0) {
                    addresses[0].apply{
                        title(this.getAddressLine(0))
                            Log.d("MapsActivity","0: ${this.getAddressLine(0)} \n")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace() // getFromLocation() may sometimes fail
        }
    }

    private fun checkPermission(){
        multipleLocationPermissionRequest()


    }

    private fun multipleLocationPermissionRequest(){
        val checkPermission = PermissionPolicy.location.let{array->
            array.all { p->
                ContextCompat.checkSelfPermission(this,p) == PackageManager.PERMISSION_GRANTED }
            }
        val requestPermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(),this)

        when {
            checkPermission -> { PermissionPolicy.defaultGrant("all grant")}
            /*한번 거절후 다음시작부터 적용*/
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                PermissionPolicy.ration(Manifest.permission.ACCESS_COARSE_LOCATION)
                requestPermissionsLauncher.launch(PermissionPolicy.location)
            }
            else -> {
                Log.d("PermissionPolicy","new request!!")
                requestPermissionsLauncher.launch(PermissionPolicy.location)
            }
        }
    }

    private fun soloLocationPermissionRequest(){
        val checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){}
        when {
            checkPermission == PackageManager.PERMISSION_GRANTED -> {
                PermissionPolicy.defaultGrant("")
            }
            /*한번 거절후 다음시작부터 적용*/
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                PermissionPolicy.ration(Manifest.permission.ACCESS_COARSE_LOCATION)
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }
}
