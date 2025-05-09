package com.study.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoSearchResponse(
    val documents: List<KaKaoPlaceItem>,
)

data class KaKaoPlaceItem(
    val id: Int,
    @field:JsonProperty("place_name")
    val placeName: String,
)
