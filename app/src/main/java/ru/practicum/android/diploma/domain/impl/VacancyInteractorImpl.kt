package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.api.favorites.FavoritesRepository
import ru.practicum.android.diploma.domain.api.vacancy.VacancyInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

class VacancyInteractorImpl(
    private val vacancies: VacanciesRepository, private val favorites: FavoritesRepository
) : VacancyInteractor {

    override suspend fun get(id: String): Resource<Vacancy> {
        val offlineVacancy = favorites.get(id) ?: return vacancies.getVacancy(id)
        return Resource.Success(offlineVacancy)
    }

    override suspend fun getIsFavorite(id: String) = favorites.get(id) != null
    override suspend fun setIsFavorite(vacancy: Vacancy, value: Boolean): Boolean {
        if (vacancy.id.isBlank()) return false
        if (value) {
            favorites.addVacancy(vacancy)
        } else {
            favorites.removeVacancy(vacancy.id)
        }
        return getIsFavorite(vacancy.id)
    }
}
