package com.study

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class LocationSearchBatchApplication

fun main(args: Array<String>) {
    runApplication<LocationSearchBatchApplication>(*args)
}

