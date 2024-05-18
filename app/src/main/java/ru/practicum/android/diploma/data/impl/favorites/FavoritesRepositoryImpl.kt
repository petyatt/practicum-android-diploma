package ru.practicum.android.diploma.data.impl.favorites

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converters.VacancyDbConverter
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.domain.api.favorites.FavoritesRepository
import ru.practicum.android.diploma.domain.models.VacancyDescription

class FavoritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val vacancyDbConverter: VacancyDbConverter
) : FavoritesRepository {
    override fun getAllVacancies(): Flow<List<VacancyDescription>> = flow {
        val vacancies = appDatabase.vacancyDao().getAllVacancies()
        emit(vacancyDbConverter.convert(vacancies))
    }

    override suspend fun addVacancy(vacancy: VacancyDescription) {
        appDatabase.vacancyDao().addVacancy(vacancyDbConverter.convertToEntity(vacancy))
    }

    override suspend fun removeVacancy(vacancy: VacancyDescription) {
        val vacanciesId = appDatabase.vacancyDao().getAllVacancies().map { it.id }
        if (vacanciesId.contains(vacancy.id)) {
            appDatabase.vacancyDao().removeVacancy(vacancy.id)
        }
    }

    override fun checkVacancy(vacancyId: String) = flow {
        val answer = appDatabase.vacancyDao().getVacancyById(vacancyId) != null
        emit(answer)
    }
}
