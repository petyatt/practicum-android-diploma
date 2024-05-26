package ru.practicum.android.diploma.domain.api.filters

import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.util.Resource

interface AreasRepository {
    suspend fun getCountries(): Resource<List<Area>>

    suspend fun getAreas(parentId: String): Resource<List<Area>>
}
