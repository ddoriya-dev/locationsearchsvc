package com.study.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ClientHttpConnector
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.Connection
import reactor.netty.http.client.HttpClient
import reactor.netty.tcp.TcpClient
import java.util.concurrent.TimeUnit

@Configuration
class WebClientConfig {
    companion object {
        private const val DEFAULT_TIMEOUT = 300
    }

    @Bean
    fun getDefaultWebClient(): WebClient {
        return getWebClient(1000, 1000, DEFAULT_TIMEOUT)
    }

    private fun getWebClient(connectionTimeout: Int, readTimeout: Int, writeTimeout: Int): WebClient {
        val tcpClient = TcpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, connectionTimeout) // Connection Timeout
            .doOnConnected { connection: Connection ->
                connection.addHandlerLast(ReadTimeoutHandler(readTimeout.toLong(), TimeUnit.MILLISECONDS)) // Read Timeout
                    .addHandlerLast(WriteTimeoutHandler(writeTimeout.toLong(), TimeUnit.MILLISECONDS))
            } // Write Timeout
        val connector: ClientHttpConnector = ReactorClientHttpConnector(HttpClient.from(tcpClient))
        return WebClient.builder().clientConnector(connector).build()
    }

}
