package com.yunwoon.coupangeatsproject.src.address

import com.yunwoon.coupangeatsproject.src.address.naverModels.naverResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NaverMapRetrofitInterface {

    @GET("/map-reversegeocode/v2/gc")
    fun getGeocoding(
        @Header("X-NCP-APIGW-API-KEY-ID") id : String,
        @Header("X-NCP-APIGW-API-KEY") key : String,
        @Query("coords", encoded = true) coords : String, // 입력 좌표
        @Query("orders", encoded = true) orders : String = "roadaddr", // 변환 작업 이름 addr (지번명), roadaddr (도로명)
        @Query("output") output : String = "json" // 출력 형식 json
    ): Call<naverResponse>

}