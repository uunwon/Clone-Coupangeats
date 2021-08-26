package com.yunwoon.coupangeatsproject.config

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    init { instance = this }

    // 테스트 서버 주소
    private val API_URL = "https://dev.vicion.shop/"

    // 실 서버 주소
    // val API_URL = ""

    // 도로명 검색 서버 주소
    private val ROAD_API_URL = "https://www.juso.go.kr/"

    // 지오코딩 서버 주소
    private val NAVER_API_URL = "https://naveropenapi.apigw.ntruss.com/"

    // 부트페이 서버 주소
    private val BOOT_PAY_API_URL = "https://api.bootpay.co.kr/"

    companion object {
        lateinit var instance : ApplicationClass

        // 만들어져있는 SharedPreferences 를 사용해야합니다. 재생성하지 않도록 유념해주세요
        lateinit var sSharedPreferences: SharedPreferences
        lateinit var sEditor: SharedPreferences.Editor

        // JWT Token Header 키 값
        const val X_ACCESS_TOKEN = "X-ACCESS-TOKEN"

        // Retrofit 인스턴스, 앱 실행시 한번만 생성하여 사용합니다.
        lateinit var sRetrofit: Retrofit
        lateinit var roadRetrofit: Retrofit
        lateinit var geoRetrofit: Retrofit
        lateinit var bootRetrofit: Retrofit
    }

    // 앱이 처음 생성되는 순간, SP를 새로 만들어주고, 레트로핏 인스턴스를 생성합니다.
    @SuppressLint("CommitPrefEdits")
    override fun onCreate() {
        super.onCreate()
        sSharedPreferences =
            applicationContext.getSharedPreferences("COUPANG_EATS_APP", MODE_PRIVATE)
        sEditor = sSharedPreferences.edit()

        sEditor.putString("loginJwtToken", null).apply()
        sEditor.putInt("addressPage", 1).apply()

        // 레트로핏 인스턴스 생성
        initRetrofitInstance()
    }

    // 레트로핏 인스턴스를 생성하고, 레트로핏에 각종 설정값들을 지정해줍니다.
    // 연결 타임아웃시간은 5초로 지정이 되어있고, HttpLoggingInterceptor를 붙여서 어떤 요청이 나가고 들어오는지를 보여줍니다.
    private fun initRetrofitInstance() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .build()

        // sRetrofit 이라는 전역변수에 API url, 인터셉터, Gson을 넣어주고 빌드해주는 코드
        // 이 전역변수로 http 요청을 서버로 보내면 됩니다.
        sRetrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        roadRetrofit = Retrofit.Builder()
            .baseUrl(ROAD_API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        geoRetrofit = Retrofit.Builder()
            .baseUrl(NAVER_API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bootRetrofit = Retrofit.Builder()
            .baseUrl(BOOT_PAY_API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}