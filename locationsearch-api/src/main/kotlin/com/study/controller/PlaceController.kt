package com.study.controller

import com.study.dto.PlaceKeywordRankResponse
import com.study.dto.PlaceResponse
import com.study.service.PlaceService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PlaceController (
    private val placeService: PlaceService,
){
    private val log = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/place")
    suspend fun getPlaces(@RequestParam("q") keyword: String): ResponseEntity<PlaceResponse> {
        log.debug("placeRequest: $keyword")
        val placeResponse = placeService.getPlaces(keyword)
        return ResponseEntity(placeResponse, HttpStatus.OK)
    }

    @GetMapping("/place/keyword-rank")
    suspend fun getKeywordRank(): ResponseEntity<PlaceKeywordRankResponse> {
        val placeResponse = placeService.getKeywordRank()
        return ResponseEntity(placeResponse, HttpStatus.OK)
    }
}
