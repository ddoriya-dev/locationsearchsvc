package com.study.dto

data class PlaceResponse(
    val keyword: String,
    val places: List<Place>,
) {
    fun mergePlaceResponse(other: PlaceResponse): PlaceResponse {
        val normalized = normalize()
        val normalizedOther = other.normalize()

        val commonPlaces = normalized.filter { it in normalizedOther.toSet() }
        val placesOnly = normalized.filterNot { it in commonPlaces }
        val otherOnly = normalizedOther.filterNot { it in normalized.toSet() }

        return PlaceResponse(keyword, (commonPlaces + placesOnly + otherOnly).map { Place(it) })
    }

    private fun normalize(): List<String> {
        return places.map {
            it.title.lowercase()
                .replace(Regex("<.*?>"), "")
                .replace(Regex("\\s+"), "")
        }
    }
}

data class Place(
    val title: String,
)
