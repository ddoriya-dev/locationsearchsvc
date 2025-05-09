package com.study.repository

import com.study.model.PlaceDataRaw
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface PlaceDataRawRepository : JpaRepository<PlaceDataRaw, Long>{
    fun findByCreateAtBetween(start: LocalDateTime, end: LocalDateTime): List<PlaceDataRaw>
}
