package ru.practicum.android.diploma.domain.api.filters

import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.util.Resource

interface RegionsInteractor {
    suspend fun search(country: String, name: String): Resource<List<Area>>
}
