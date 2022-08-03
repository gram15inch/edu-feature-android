package com.nuhlp.googlemapapi

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
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
  1. 더 자세한 위치 요청 -> 여러개의 권한 필요 (자세한 위치 단독으로 요쳥 불가 사용자가 정확도를 선택 할 수 있어야함 [ver>31])
  2. 여러개의 권한 요청 -> AL 생성시 여러개의 권한을 담당,처리할 수 있는 Contracts , Callback 필요
    2-1. Contracts -> ActivityResultContracts.RequestMultiplePermissions()
    2-2. Callback  -> (Map<String,Boolean>) -> Unit 타입으로 만든후 권한별 승인여부 확인후 개별처리하는 로직 생성

 ** 8/1 **
  1. 위치 요청(권한승인후) -> 통합위치정보 제공자 클라이언트 필요(FusedLocationProviderClient)
  2. 위 클라이언트로 마지막 알려진 위치 요청 -> 통제자 위치업데이트 필요 (일정한 간격을 설정해서 업데이트 받는식으로 위치를 받음)
    2-1. 통제자 -> 구글 play 서비스에서 공통적으로 사용하는 위치 API (구글 play api 를 사용하는 앱끼리 최신위치정보를 공유::자원절약)
  3. 위치 업데이트 요청 -> 기기에 시스템설정(gps 등..) 확인 필요
  4. 시스템설정 확인 요청 ->  SettingsClient, Task<LocationSettingsResponse> 필요
    4-1. SettingsClient -> 시스템설정을 변경할수있는 접근자로 앱과 연결해 사용 todo 확인필요
    4-2. Task<LocationSettingsResponse> -> 시스템설정 확인 결과로 확인여부에 따라 실행할수있는 다른 리스너#[5]를 붙여 사용
        * SC.checkLocationSettings 로 생성 (파라미터에 넣을 LocationSettingsRequest, LocationRequest 필요)
        4-2-1. LocationRequest -> 위치를 특정하는데 필요한 요청서(간격/정확도/전력)
            * .create() 메서드로 인스턴스화 후 설정값 초기화
               - interval = 10000  (기본 업데이트 간격)
               - fastestInterval = 5000 (앱이 사용할 수 있는 가장빠른 간격)
               - priority = LocationRequest.PRIORITY_HIGH_ACCURACY (정확도/전력소비량) :: 앱의 권한 정확도와 설정의 간격들의 조합
        4-2-2. LocationSettingsRequest -> 하나이상의 LocationRequest 의 추가 필요 .addLocationRequest(locationRequest)로 추가
            * .Builder() 메서드로 인스턴스화 후 추가 (후에 파라미터로 넣을때 .build() 사용)
    4-3. Task 로 결과 확인
  5. Task 로 결과처리 리스너 연결 (성공/addOnSuccessListener, 실패/addOnFailureListener)
    5-1. 성공 -> 여기부터 현재위치 접근 가능
    5-2. 실패 -> 사용자에게 위치 설정을 수정할 수 있는 권한을 요청
        5-2-1. 실패 리스너 에서 파라미터로 받은 예외에서 ResolvableApiException 타입인지확인
        5-2-2. 타입 확인된 예외로 startResolutionForResult 호출해 설정 수정 권한 입력받음

 ** 8/2 **
  1.


* */


class MapsActivity : AppCompatActivity(),
    OnMapReadyCallback,
    OnMyLocationButtonClickListener,
    OnMyLocationClickListener,
    ActivityResultCallback<Map<String,Boolean>>  {

    private var isOnGPS = false
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    val LATLNG = LatLng(37.566418,126.977943)
    val LATLNG_DONGBAEK = LatLng(37.2775928,127.1525655)
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        multipleLocationPermissionRequest()


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }



    /* map */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        setCamera(LATLNG_DONGBAEK)

        locationSettingRequest()
        updateLocation()
        showGps(mMap)
        // todo 여기부터 책
        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)
    }

    @SuppressLint("MissingPermission")
    private fun updateLocation() {

        locationCallback = object: LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.let{
                    for((i,location) in it.locations.withIndex()){
                        Log.d("MapsActivity","$i ${location.latitude}, ${location.longitude}")
                        setLastLocation(location)
                    }
                }
            }
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper())

    }
    private fun setMarker(latLng: LatLng) {
        val bitmapDrawable = bitmapDescriptorFromVector(this, R.drawable.marker)
        val discriptor = bitmapDrawable
        val markerOptions = MarkerOptions()
            .position(latLng)
            .icon(discriptor)
        markerOptions.setAddress()
        /*.title("marker in Seoul City Hall")
            .snippet("37.566418,126.977943")*/
        mMap.addMarker(markerOptions)
    }
    private fun setCamera(latLng: LatLng) {
        val cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(20.0f)
            .build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        mMap.moveCamera(cameraUpdate)
    }

    override fun onActivityResult(permissions: Map<String, Boolean>) = permissions.forEach{
        when{
            it.key == Manifest.permission.ACCESS_COARSE_LOCATION && it.value ->{PermissionPolicy.defaultGrant("ACCESS_COARSE_LOCATION")
                showGps(mMap)
            }
            it.key == Manifest.permission.ACCESS_FINE_LOCATION && it.value->{PermissionPolicy.defaultGrant("ACCESS_FINE_LOCATION")}
            else ->{PermissionPolicy.defaultReject(it.key)}
        }
    }
    private  fun MarkerOptions.setAddress(){
        try {// todo 주소 클래스 분석
            val geo =
                Geocoder(this@MapsActivity, Locale.getDefault())
            val addresses: List<Address> = geo.getFromLocation(LATLNG_DONGBAEK.latitude,LATLNG_DONGBAEK.longitude, 1)
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
    fun moveCameraLauncher(map:GoogleMap,latLng: LatLng){
        val cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(17.0f)
            .build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        map.moveCamera(cameraUpdate)
    }
    override fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG)
            .show()
    }
    override fun onMyLocationButtonClick(): Boolean {

        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).

        if(!isOnGPS) {
            isOnGPS = true
            Toast.makeText(this, "MyLocation button clicked : ON", Toast.LENGTH_SHORT).show()
        }
        else
        {
            isOnGPS= false
            Toast.makeText(this, "MyLocation button clicked : OFF", Toast.LENGTH_SHORT).show()
        }
        return false
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
    fun showGps(mMap:GoogleMap){
        val checkP= ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if(checkP == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        }
    }




    /* permission */
    private fun multipleLocationPermissionRequest(){
        val checkPermission = PermissionPolicy.location.let{array->
            array.all { p->
                ContextCompat.checkSelfPermission(this,p) == PackageManager.PERMISSION_GRANTED }
            }
        val requestPermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(),this)

        when {
            checkPermission -> { PermissionPolicy.defaultGrant("all grant")
            }
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

    private fun locationSettingRequest(){

        val REQUEST_CHECK_SETTINGS = 0x1
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener { locationSettingsResponse ->
            // All location settings are satisfied. The client can initialize
            // location requests here.
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
                    Log.d("MapsActivity","========= catch")

                }
            }
        }

    }

    private fun setLastLocation(lastLocation:Location) {
        mMap.clear()
        LatLng(lastLocation.latitude,lastLocation.longitude).let{
            setCamera(it)
            setMarker(it)
        }
    }

    // ***** 사용안함 *****
    private fun withCurrentLatLng(callback:(LatLng)->Unit) {
        val checkP = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (checkP == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                callback(LatLng(it.latitude, it.longitude))
                Log.d("MapsActivity","${it.latitude} ${it.longitude}")
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
