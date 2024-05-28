package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.sharedpreferences.FilterIneractor
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesRepository
import ru.practicum.android.diploma.domain.models.Filter

class FilterIneractorImpl(private var sharedpreferencesRepository: SharedPreferencesRepository) : FilterIneractor {
    override suspend fun save(filter: Filter) {
        sharedpreferencesRepository.save(filter)
    }

    override suspend fun get(): Filter? = sharedpreferencesRepository.get()

    override suspend fun clear() {
        sharedpreferencesRepository.clear()
    }
}
