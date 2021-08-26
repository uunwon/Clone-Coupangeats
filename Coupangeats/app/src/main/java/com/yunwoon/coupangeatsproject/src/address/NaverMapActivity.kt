package com.yunwoon.coupangeatsproject.src.address

import android.os.Bundle
import android.util.Log
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.yunwoon.coupangeatsproject.BuildConfig
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityNaverMapBinding
import com.yunwoon.coupangeatsproject.src.address.naverModels.naverResponse

class NaverMapActivity : BaseActivity<ActivityNaverMapBinding>(ActivityNaverMapBinding::inflate),
    OnMapReadyCallback, NaverMapActivityView {
    private val key = BuildConfig.NAVER_CLIENT_ID

    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private lateinit var uiSettings : UiSettings

    private var flag = 0
    private val marker = Marker()

    private var latitude : Double? = null
    private var longitude : Double? = null

    private val PERMISSIONS = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient(key)

        binding.mapFragment.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        binding.naverMapImageButtonBack.setOnClickListener { finish() }
    }

    // 권한 허용
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap

        naverMap.minZoom = 3.0
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.NoFollow

        uiSettings = naverMap.uiSettings
        uiSettings.isCompassEnabled = true
        uiSettings.isLocationButtonEnabled = true

        naverMap.addOnLocationChangeListener { location ->
            // showCustomToast("${location.latitude}, ${location.longitude}")

            if(flag++ == 0) {
                val cameraUpdate = CameraUpdate.scrollTo(LatLng(location.latitude, location.longitude))

                marker.position = LatLng(location.latitude, location.longitude)
                marker.icon = OverlayImage.fromResource(R.drawable.navermap_default_marker_icon_black)

                marker.map = naverMap
                naverMap.moveCamera(cameraUpdate)

                val coords = "${location.longitude},${location.latitude}"
                Log.d("NaverMapActivity", "coords ? $coords")

                showLoadingDialog(this)
                NaverMapService(this).tryGetGeocoding(coords)
            }
        }
    }

    override fun onGetGeocodingSuccess(response: naverResponse) {
        dismissLoadingDialog()
        if(response.status.name == "ok") {
            var address = ""
            var addressDetail = ""
            for(i in response.results) {
                if(i.land.addition0.value.isNotEmpty())
                    address = i.land.addition0.value
                else
                    address = i.region.area3.name

                addressDetail = "${i.region.area1.name} ${i.region.area2.name} ${i.land.name} ${i.land.number1}"
            }
            binding.naverTextAddress.text = address
            binding.naverTextAddressDetail.text = addressDetail
        } else {
            showCustomToast("지도 불러오기 실패")
        }
    }

    override fun onGetGeocodingFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast("오류 : $message")
    }
}