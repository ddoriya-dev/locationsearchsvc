package com.study

import com.study.repository.PlaceDataRawRepository
import com.study.repository.PlaceDataStatRepository
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class BatchScheduler(
    private val placeDataRawRepository: PlaceDataRawRepository,
    private val placeDataStatRepository: PlaceDataStatRepository,
) {
    val log = LoggerFactory.getLogger(BatchScheduler::class.java)

    @Scheduled(cron = "0 * * * * *")
    @Transactional
    fun updatePlaceDataStats() {
        val now = LocalDateTime.now()
        val oneMinuteAgo = now.minusMinutes(1)

        val keywordCounts = placeDataRawRepository.findByCreateAtBetween(oneMinuteAgo, now)
            .groupBy { it.keyword }
            .mapValues { it.value.size.toLong() }

        keywordCounts.forEach { (keyword, count) ->
            placeDataStatRepository.upsert(keyword, count)
        }

        log.debug("Updated PlaceDataStat with {} keywords at {}", keywordCounts.size, now)
        val stats = placeDataStatRepository.findAll()
        stats.forEach { println("  Stat: $it") }
    }
}
