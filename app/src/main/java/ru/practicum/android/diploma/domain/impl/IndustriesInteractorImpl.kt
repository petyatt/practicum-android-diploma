package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.filters.IndustriesInteractor
import ru.practicum.android.diploma.domain.api.filters.IndustriesRepository
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

class IndustriesInteractorImpl(private val repository: IndustriesRepository) : IndustriesInteractor {

    private val cache = mutableListOf<Industry>()
    override suspend fun search(name: String): Resource<List<Industry>> {
        fillCache()
        return if (cache.isEmpty()) {
            Resource.Failed()
        } else {
            Resource.Success(cache.filter { it.name.contains(name, ignoreCase = true) })
        }
    }

    private suspend fun fillCache() {
        if (cache.isNotEmpty()) return
        val result = repository.get()
        if (result is Resource.Success) cache.addAll(result.data)
    }
}
