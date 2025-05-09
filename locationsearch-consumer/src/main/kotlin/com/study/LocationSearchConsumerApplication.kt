package com.study

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LocationSearchConsumerApplication

fun main(args: Array<String>) {
    runApplication<LocationSearchConsumerApplication>(*args)
}

