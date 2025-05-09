package com.study.dto

data class NaverSearchResponse(
    val items: List<NaverPlaceItem>
)

data class NaverPlaceItem(
    val title: String,
)
