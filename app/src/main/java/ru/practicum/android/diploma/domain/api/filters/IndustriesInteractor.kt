package ru.practicum.android.diploma.domain.api.filters

import ru.practicum.android.diploma.domain.models.Industry

interface IndustriesInteractor {
    suspend fun search(name: String): List<Industry>
}
