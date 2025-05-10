package com.study.controller

import com.study.dto.PlaceKeywordRankResponse
import com.study.dto.PlaceResponse
import com.study.dto.PlaceStat
import com.study.service.PlaceService
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import kotlin.test.assertEquals

class PlaceControllerTest {

    private val placeService: PlaceService = mock()
    private val controller = PlaceController(placeService)

    @Test
    fun `getPlaces should return PlaceResponse with OK status`() = runBlocking {
        val keyword = "커피"
        val expectedResponse = PlaceResponse(keyword, listOf())
        whenever(placeService.getPlaces(keyword)).thenReturn(expectedResponse)

        val result: ResponseEntity<PlaceResponse> = controller.getPlaces(keyword)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(expectedResponse, result.body)
    }

    @Test
    fun `getKeywordRank should return PlaceResponse with OK status`() = runBlocking {
        val expectedResponse = PlaceKeywordRankResponse(listOf(PlaceStat("커피", 1)))
        whenever(placeService.getKeywordRank()).thenReturn(expectedResponse)

        // When
        val result: ResponseEntity<PlaceKeywordRankResponse> = controller.getKeywordRank()

        // Then
        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals(expectedResponse, result.body)
    }
}
