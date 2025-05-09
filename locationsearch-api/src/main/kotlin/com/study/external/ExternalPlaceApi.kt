package com.study.external

import com.study.config.ExternalConfig
import com.study.dto.KakaoSearchResponse
import com.study.dto.NaverSearchResponse
import com.study.dto.Place
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException

enum class ExternalPlaceApi {
    KAKAO {
        override suspend fun callApi(
            keyword: String,
            externalConfig: ExternalConfig,
            webClient: WebClient
        ): List<Place> {
            return callExternalApi(
                url = "${externalConfig.kakao.url}?query=$keyword&size=5",
                headers = mapOf(
                    "Authorization" to "KakaoAK ${externalConfig.kakao.token}"
                ),
                webClient = webClient
            ) { response: KakaoSearchResponse ->
                response.documents.map { Place(title = it.placeName) }
            }
        }

    },
    NAVER {
        override suspend fun callApi(
            keyword: String,
            externalConfig: ExternalConfig,
            webClient: WebClient
        ): List<Place> {
            return callExternalApi(
                url = "${externalConfig.naver.url}?query=$keyword&display=5",
                headers = mapOf(
                    "X-Naver-Client-Id" to externalConfig.naver.clientId,
                    "X-Naver-Client-Secret" to externalConfig.naver.clientSecret
                ),
                webClient = webClient
            ) { response: NaverSearchResponse ->
                response.items.map { Place(title = it.title) }
            }
        }
    };

    abstract suspend fun callApi(
        keyword: String,
        externalConfig: ExternalConfig,
        webClient: WebClient
    ): List<Place>

    suspend inline fun <reified T> callExternalApi(
        url: String,
        headers: Map<String, String>,
        webClient: WebClient,
        responseMapper: (T) -> List<Place>
    ): List<Place> {
        return try {
            val response = webClient.get()
                .uri(url)
                .apply { headers.forEach { (key, value) -> header(key, value) } }
                .retrieve()
                .bodyToMono(T::class.java)
                .awaitSingle()
            responseMapper(response)
        } catch (e: WebClientResponseException) {
            throw RuntimeException("API 호출 실패: ${e.message}", e)
        }
    }
}
