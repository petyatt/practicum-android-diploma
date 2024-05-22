package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.favorites.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.favorites.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesInteractorImpl(
    private val favouriteRepository: FavoritesRepository
) : FavoritesInteractor {
    override fun getAllVacancies(): Flow<List<Vacancy>> {
        return favouriteRepository.getAllVacancies()
    }

    override suspend fun addVacancy(vacancy: Vacancy) {
        favouriteRepository.addVacancy(vacancy)
    }

    override suspend fun removeVacancy(id: String) {
        favouriteRepository.removeVacancy(id)
    }

    override fun checkVacancy(id: String): Flow<Boolean> {
        return favouriteRepository.checkVacancy(id)
    }
}
