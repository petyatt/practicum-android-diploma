package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.api.favorites.FavoritesRepository
import ru.practicum.android.diploma.domain.api.vacancy.VacancyInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

class VacancyInteractorImpl(
    private val vacancies: VacanciesRepository,
    private val favorites: FavoritesRepository
) : VacancyInteractor {
    override suspend fun getVacancy(id: String): Flow<Resource<Vacancy>> {
        val offlineVacancy = favorites.get(id) ?: return vacancies.getVacancy(id)
        return flow { emit(Resource.Success(offlineVacancy)) }
    }
}
