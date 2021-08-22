package com.yunwoon.coupangeatsproject.src.address

import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource
import com.yunwoon.coupangeatsproject.BuildConfig
import com.yunwoon.coupangeatsproject.config.BaseActivity
import com.yunwoon.coupangeatsproject.databinding.ActivityNaverMapBinding

class NaverMapActivity : BaseActivity<ActivityNaverMapBinding>(ActivityNaverMapBinding::inflate), OnMapReadyCallback {
    private val key = BuildConfig.NAVER_CLIENT_ID

    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    private lateinit var mapView : MapView
    private lateinit var uiSettings : UiSettings

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
        binding.naverMapView.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        binding.naverMapImageButtonBack.setOnClickListener { finish() }
    }

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

        // https://lnfo-studio.com/5?category=894028
        // https://navermaps.github.io/android-map-sdk/guide-ko/4-2.html
        // https://stickode.com/detail.html?no=1494
        // https://api.ncloud-docs.com/docs/ai-naver-mapsstaticmap-raster
        ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.NoFollow

        uiSettings = naverMap.uiSettings
        uiSettings.isCompassEnabled = true
        uiSettings.isLocationButtonEnabled = true
    }
}