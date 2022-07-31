package com.nuhlp.googlemapapi.util

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


/* todo 문서 학습
* 1. map gps 요청 -> 권한 필요
* 2. 권한 요청 -> ActivityResultLauncher 필요
* 3. ARL 요청 -> -> ActivityResult 필요
* 4. AR 요청 -> Contracts, Callback 필요
*   4-1. Contracts 학습 ->  Intent 생성, 결과코드 해독 등
*   4-2. Callback 학습 -> 결과처리 :: 호출 액티비티가 재시작 되서 작업순서가 사라져도 결과를 처리할 수 있게 결과처리함수를 직접주입하기 위해 사용
* 5.
* */
object PermissionPolicy  {
    val tag = "PermissionPolicy"


    fun defaultReject(){
        Log.d(tag,"call rejectPermission!")

    }
    fun defaultGrant(){
        Log.d(tag,"call grantPermission!")

    }
    fun ration(permission: String) {
     Log.d(tag,"call ration!")
    //todo ca.showInContextUI(...)
    }

}