package ru.practicum.android.diploma.domain.api.filters

import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.util.Resource

interface AreasRepository {
    suspend fun getCountries(): Resource<List<Country>>
}
