package com.study

import com.study.model.PlaceDataRaw
import com.study.model.PlaceDataStat
import com.study.repository.PlaceDataRawRepository
import com.study.repository.PlaceDataStatRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.slf4j.LoggerFactory
import org.springframework.test.util.ReflectionTestUtils
import java.time.LocalDateTime

class BatchSchedulerTest {

    private lateinit var batchScheduler: BatchScheduler
    private val placeDataRawRepository: PlaceDataRawRepository = mock()
    private val placeDataStatRepository: PlaceDataStatRepository = mock()

    private val now = LocalDateTime.now()
    private val oneMinuteAgo = now.minusMinutes(1)

    @BeforeEach
    fun setUp() {
        batchScheduler = BatchScheduler(placeDataRawRepository, placeDataStatRepository)
        ReflectionTestUtils.setField(batchScheduler, "log", LoggerFactory.getLogger(BatchScheduler::class.java))
    }

    @Test
    fun `updatePlaceDataStats should process keyword counts and update stats`() {
        val rawData = listOf(
            PlaceDataRaw(keyword ="keyword1", createAt = now),
            PlaceDataRaw(keyword ="keyword2", createAt = now),
            PlaceDataRaw(keyword ="keyword2", createAt = now),
        )

        whenever(placeDataRawRepository.findByCreateAtBetween(oneMinuteAgo, now))
            .thenReturn(rawData)

        val stat = listOf(
            PlaceDataStat(keyword = "keyword1", count =  1L),
            PlaceDataStat(keyword = "keyword2", count =  2L),
        )
        whenever(placeDataStatRepository.findAll())
            .thenReturn(stat)

        batchScheduler.updatePlaceDataStats()

        verify(placeDataRawRepository).findByCreateAtBetween(oneMinuteAgo, now)
        verify(placeDataStatRepository).upsert("keyword1", 1L)
        verify(placeDataStatRepository).upsert("keyword2", 2L)
        verify(placeDataStatRepository).findAll()
    }

    @Test
    fun `updatePlaceDataStats should handle empty data`() {
        // Given
        whenever(placeDataRawRepository.findByCreateAtBetween(oneMinuteAgo, now))
            .thenReturn(emptyList())
        whenever(placeDataStatRepository.findAll())
            .thenReturn(emptyList())

        // When
        batchScheduler.updatePlaceDataStats()

        // Then
        verify(placeDataRawRepository).findByCreateAtBetween(oneMinuteAgo, now)
        verify(placeDataStatRepository, never()).upsert(any(), any())
        verify(placeDataStatRepository).findAll()
    }

}
