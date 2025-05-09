package com.study.dto

data class PlaceKeywordRankResponse(
    val placeStats: List<PlaceStat>
)

data class PlaceStat(
    val keyword: String,
    val count: Long,
)
