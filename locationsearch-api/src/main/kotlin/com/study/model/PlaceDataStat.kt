package com.study.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("place_data_stat")
data class PlaceDataStat(
    @Id
    val id: Long? = null,

    val keyword: String,

    val count: Long = 0,

    @Column("create_at")
    val createAt: LocalDateTime = LocalDateTime.now()
)
