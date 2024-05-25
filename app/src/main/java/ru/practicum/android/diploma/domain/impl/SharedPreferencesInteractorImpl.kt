package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesInteractor
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesRepository
import ru.practicum.android.diploma.domain.models.Filter

class SharedPreferencesInteractorImpl(
    private var sharedpreferencesRepository: SharedPreferencesRepository
) : SharedPreferencesInteractor {
    override suspend fun save(filter: Filter?) {
        sharedpreferencesRepository.save(filter)
    }

    override fun get(): Filter? {
        return sharedpreferencesRepository.get()
    }

    override suspend fun clear() {
        sharedpreferencesRepository.clear()
    }
}
