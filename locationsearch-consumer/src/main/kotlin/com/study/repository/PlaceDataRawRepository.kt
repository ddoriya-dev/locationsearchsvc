package com.study.repository

import com.study.model.PlaceDataRaw
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PlaceDataRawRepository : JpaRepository<PlaceDataRaw, Long>
