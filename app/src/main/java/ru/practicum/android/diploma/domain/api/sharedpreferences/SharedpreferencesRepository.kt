package ru.practicum.android.diploma.domain.api.sharedpreferences

import ru.practicum.android.diploma.domain.models.Filter

interface SharedpreferencesRepository {
    suspend fun save(filter: Filter?)
    fun get(): Filter?
    suspend fun clear()
}
