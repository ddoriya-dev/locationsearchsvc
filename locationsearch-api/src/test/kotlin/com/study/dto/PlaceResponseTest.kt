package com.study.dto

import kotlin.test.Test
import kotlin.test.assertEquals

class PlaceResponseTest {

    @Test
    fun `mergePlaceResponse should merge two PlaceResponse objects correctly`() {
        val response1 = PlaceResponse(
            keyword = "커피",
            places = listOf(
                Place("스타벅스"),
                Place("스타벅스강남점"),
                Place("투썸강남점"),
                Place("투썸"),
                Place("스타벅스코엑스몰점"),
            )
        )

        val response2 = PlaceResponse(
            keyword = "커피",
            places = listOf(
                Place("스타벅스"),
                Place("투썸강남점"),
                Place("스타벅스강남점"),
                Place("투썸판교점"),
                Place("스타벅스판교점"),
            )
        )

        val mergedResponse = response1.mergePlaceResponse(response2)
        val expectedPlaces = listOf(
            Place("스타벅스"),
            Place("스타벅스강남점"),
            Place("투썸강남점"),
            Place("투썸"),
            Place("스타벅스코엑스몰점"),
            Place("투썸판교점"),
            Place("스타벅스판교점"),
        )
        assertEquals("커피", mergedResponse.keyword)
        assertEquals(expectedPlaces, mergedResponse.places)
    }
}
