package ru.practicum.android.diploma.domain.api.filters

import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.util.Resource

interface CountriesInteractor {
    suspend fun search(name: String): Resource<List<Area>>
}
