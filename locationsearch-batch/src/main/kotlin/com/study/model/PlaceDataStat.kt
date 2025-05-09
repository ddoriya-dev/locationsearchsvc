package com.study.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "place_data_stat")
data class PlaceDataStat(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val keyword: String,
    
    @Column(nullable = false)
    val count: Long = 0,

    @Column(name = "create_at", nullable = false)
    val createAt: LocalDateTime = LocalDateTime.now(),
) {
    constructor() : this(id = null, keyword = "", count = 0, createAt = LocalDateTime.now())
}
