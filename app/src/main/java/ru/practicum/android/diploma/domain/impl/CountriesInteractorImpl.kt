package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.filters.AreasRepository
import ru.practicum.android.diploma.domain.api.filters.CountriesInteractor
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.util.Resource

class CountriesInteractorImpl(private val repository: AreasRepository) : CountriesInteractor {
    private val cache = mutableListOf<Area>()
    override suspend fun search(name: String): Resource<List<Area>> {
        fillCache()
        return if (cache.isEmpty()) {
            Resource.Failed()
        } else {
            Resource.Success(cache.filter { it.name.contains(name, ignoreCase = true) })
        }
    }

    private suspend fun fillCache() {
        if (cache.isNotEmpty()) return
        val result = repository.getCountries()
        if (result is Resource.Success) cache.addAll(result.data)
    }
}
