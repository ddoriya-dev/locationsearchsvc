package com.study.repository

import com.study.model.PlaceDataStat
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface PlaceDataStatRepository : ReactiveCrudRepository<PlaceDataStat, Long> {
    @Query("SELECT * FROM place_data_stat ORDER BY count DESC LIMIT 10")
    fun findTop10ByOrderByCountDesc(): Flux<PlaceDataStat>
}
