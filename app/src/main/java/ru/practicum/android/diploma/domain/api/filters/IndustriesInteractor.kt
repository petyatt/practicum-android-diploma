package ru.practicum.android.diploma.domain.api.filters

import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

interface IndustriesInteractor {
    suspend fun search(name: String): Resource<List<Industry>>
}
