package ru.practicum.android.diploma.domain.api.sharedpreferences

import ru.practicum.android.diploma.domain.models.Filter

interface SharedPreferencesRepository {
    suspend fun save(filter: Filter)
    suspend fun get(): Filter?
    suspend fun clear()
}
