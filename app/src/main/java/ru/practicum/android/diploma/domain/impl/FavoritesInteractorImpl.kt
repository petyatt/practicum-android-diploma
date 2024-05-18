package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.favorites.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.favorites.FavoritesRepository
import ru.practicum.android.diploma.domain.models.VacancyDescription

class FavoritesInteractorImpl(
    private val favouriteRepository: FavoritesRepository
) : FavoritesInteractor {
    override fun getAllVacancies(): Flow<List<VacancyDescription>> {
        return favouriteRepository.getAllVacancies()
    }

    override suspend fun addVacancy(vacancy: VacancyDescription) {
        favouriteRepository.addVacancy(vacancy)
    }

    override suspend fun removeVacancy(vacancy: VacancyDescription) {
        favouriteRepository.removeVacancy(vacancy)
    }

    override fun checkVacancy(id: String): Flow<Boolean> {
        return favouriteRepository.checkVacancy(id)
    }
}
