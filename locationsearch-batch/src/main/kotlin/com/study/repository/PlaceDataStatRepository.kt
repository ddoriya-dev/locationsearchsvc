package com.study.repository

import com.study.model.PlaceDataStat
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface PlaceDataStatRepository : JpaRepository<PlaceDataStat, Long> {

    @Modifying
    @Query(
        value = """
        INSERT INTO place_data_stat (keyword, count)
        VALUES (:keyword, :count)
        ON DUPLICATE KEY UPDATE
            count = count + :count
    """, nativeQuery = true
    )
    fun upsert(
        @Param("keyword") keyword: String,
        @Param("count") count: Long
    ): Int
}
