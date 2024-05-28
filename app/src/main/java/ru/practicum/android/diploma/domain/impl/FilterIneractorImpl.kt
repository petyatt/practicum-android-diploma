package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.sharedpreferences.FilterIneractor
import ru.practicum.android.diploma.domain.api.sharedpreferences.FilterRepository
import ru.practicum.android.diploma.domain.models.Filter

class FilterIneractorImpl(private var repository: FilterRepository) : FilterIneractor {
    override suspend fun save(filter: Filter) {
        repository.save(filter)
    }

    override suspend fun get(): Filter? = repository.get()
}
