package com.study

import com.study.config.ExternalConfig
import com.study.config.KakaoConfig
import com.study.config.NaverConfig
import com.study.config.WebClientConfig
import com.study.dto.Place
import com.study.dto.PlaceResponse
import com.study.external.ExternalPlaceApi
import com.study.service.PlaceService
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.web.reactive.function.client.WebClient

@SpringBootTest
class PlaceServiceTest {

    @Autowired
    private lateinit var placeService: PlaceService

    @MockBean
    private lateinit var externalConfig: ExternalConfig

    @MockBean
    private lateinit var webClientConfig: WebClientConfig

    private lateinit var mockWebClient: WebClient

    @BeforeEach
    fun setup() {
        mockWebClient = mock()
        // Mock ExternalConfig properties
        whenever(externalConfig.kakao).thenReturn(
            KakaoConfig(
            url = "https://dapi.kakao.com/v2/local/search/keyword.json",
            token = "test-kakao-token"
        )
        )
        whenever(externalConfig.naver).thenReturn(
            NaverConfig(
            url = "https://openapi.naver.com/v1/search/local.json",
            clientId = "test-naver-id",
            clientSecret = "test-naver-secret"
        )
        )
        whenever(webClientConfig.getDefaultWebClient()).thenReturn(mockWebClient)
    }

    @Test
    fun `getPlaces should merge Kakao and Naver place responses correctly`() = runBlocking {
        // Given
        val keyword = "coffee"
        val kakaoPlaces = listOf(
            Place(title = "Coffee Shop A"),
            Place(title = "Coffee Shop B")
        )
        val naverPlaces = listOf(
            Place(title = "Coffee Shop B"), // Common place
            Place(title = "Coffee Shop C")
        )

        // Mock Kakao API response
        whenever(ExternalPlaceApi.KAKAO.callApi(any(), any(), any())).thenReturn(kakaoPlaces)

        // Mock Naver API response
        whenever(ExternalPlaceApi.NAVER.callApi(any(), any(), any())).thenReturn(naverPlaces)

        // When
        val result = placeService.getPlaces(keyword)

        // Then
        assertEquals(keyword, result.keyword)
        assertEquals(3, result.places.size) // Coffee Shop A, B, C (B is common)
        assertEquals(setOf("Coffee Shop A", "Coffee Shop B", "Coffee Shop C"),
            result.places.map { it.title }.toSet())
    }

}
