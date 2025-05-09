package com.study.service

import com.study.config.ExternalConfig
import com.study.config.WebClientConfig
import com.study.dto.PlaceData
import com.study.dto.PlaceKeywordRankResponse
import com.study.dto.PlaceResponse
import com.study.dto.PlaceStat
import com.study.external.ExternalPlaceApi
import com.study.repository.PlaceDataStatRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Service

@Service
class PlaceService(
    private val externalConfig: ExternalConfig,
    private val webClientConfig: WebClientConfig,
    private val placeRabbitMqService: PlaceRabbitMqService,
    private val placeDataStatRepository: PlaceDataStatRepository,
) {
    suspend fun getPlaces(keyword: String): PlaceResponse {
        val kakaoPlaceResponse = fetchResults(ExternalPlaceApi.KAKAO, keyword)
        val naverPlaceResponse = fetchResults(ExternalPlaceApi.NAVER, keyword)

        placeRabbitMqService.sendMessage(
            PlaceData(
                keyword = keyword,
            )
        )
        return kakaoPlaceResponse.mergePlaceResponse(naverPlaceResponse)
    }

    suspend fun getKeywordRank(): PlaceKeywordRankResponse {
        return placeDataStatRepository.findTop10ByOrderByCountDesc()
            .collectList()
            .awaitSingle()
            .map { stat -> PlaceStat(stat.keyword, stat.count) }
            .let { PlaceKeywordRankResponse(it) }
    }

    private suspend fun fetchResults(api: ExternalPlaceApi, keyword: String): PlaceResponse {
        return PlaceResponse(
            keyword = keyword,
            places = api.callApi(
                keyword,
                externalConfig,
                webClientConfig.getDefaultWebClient()
            )
        )
    }
}
