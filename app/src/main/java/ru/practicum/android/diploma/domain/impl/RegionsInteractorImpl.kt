package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.filters.AreasRepository
import ru.practicum.android.diploma.domain.api.filters.RegionsInteractor
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.util.Resource

class RegionsInteractorImpl(val repository: AreasRepository) : RegionsInteractor {

    private val cache = mutableMapOf<String, List<Area>>()
    override suspend fun search(country: Area?, name: String): Resource<List<Area>> {
        fillCache(country)
        val cached = cache[country?.id ?: ""]
        return if (cached.isNullOrEmpty()) {
            Resource.Failed()
        } else {
            Resource.Success(cached.filter { it.name.contains(name, ignoreCase = true) })
        }
    }

    private suspend fun fillCache(country: Area?) {
        val cached = cache[country?.id ?: ""]
        if (cached != null) return
        val result = if (country == null) repository.getRegions() else repository.getAreas(country)
        if (result is Resource.Success) cache[country?.id ?: ""] = result.data.toList()
    }
}
