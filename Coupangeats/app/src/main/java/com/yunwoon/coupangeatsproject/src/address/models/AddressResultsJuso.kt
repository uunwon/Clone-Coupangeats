package com.yunwoon.coupangeatsproject.src.address.models

import com.google.gson.annotations.SerializedName

data class AddressResultsJuso(
    @SerializedName("detBdNmList") val detBdNmList: String,
    @SerializedName("engAddr") val engAddr: String,
    @SerializedName("rn") val rn: String,
    @SerializedName("emdNm") val emdNm: String,
    @SerializedName("zipNo") val zipNo: String,
    @SerializedName("roadAddrPart2") val roadAddrPart2: String,
    @SerializedName("emdNo") val emdNo: String,
    @SerializedName("sggNm") val sggNm: String,
    @SerializedName("jibunAddr") val jibunAddr: String,
    @SerializedName("siNm") val siNm: String,
    @SerializedName("roadAddrPart1") val roadAddrPart1: String,
    @SerializedName("bdNm") val bdNm: String,
    @SerializedName("admCd") val admCd: String,
    @SerializedName("udrtYn") val udrtYn: String,
    @SerializedName("lnbrMnnm") val lnbrMnnm: Number,
    @SerializedName("roadAddr") val roadAddr: String,
    @SerializedName("lnbrSlno") val lnbrSlno: Number,
    @SerializedName("buldMnnm") val buldMnnm: Number,
    @SerializedName("bdKdcd") val bdKdcd: String,
    @SerializedName("liNm") val liNm: String,
    @SerializedName("rnMgtSn") val rnMgtSn: String,
    @SerializedName("mtYn") val mtYn: String,
    @SerializedName("bdMgtSn") val bdMgtSn: String,
    @SerializedName("buldSlno") val buldSlno: Number,
)
