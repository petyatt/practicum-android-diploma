package ru.practicum.android.diploma.domain.api.sharedpreferences

import ru.practicum.android.diploma.domain.models.Filter

interface FilterIneractor {
    suspend fun save(filter: Filter)
    suspend fun get(): Filter?
}
