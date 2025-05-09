package com.study.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "external")
data class ExternalConfig(
    val naver: NaverConfig = NaverConfig(),
    val kakao: KakaoConfig = KakaoConfig(),
)

data class KakaoConfig(
    var url: String = "",
    var token: String = ""
)

data class NaverConfig(
    var url: String = "",
    var clientId: String = "",
    var clientSecret: String = ""
)
