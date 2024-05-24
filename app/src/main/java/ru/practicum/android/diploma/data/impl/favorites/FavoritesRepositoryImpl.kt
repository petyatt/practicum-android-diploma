package ru.practicum.android.diploma.data.impl.favorites

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converters.VacancyDbConverter
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.domain.api.favorites.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val vacancyDbConverter: VacancyDbConverter
) : FavoritesRepository {
    override fun getAllVacancies(): Flow<List<Vacancy>> = flow {
        val vacancies = appDatabase.vacancyDao().getAllVacancies()
        emit(vacancyDbConverter.convert(vacancies))
    }

    override suspend fun get(id: String): Vacancy? {
        val entity = appDatabase.vacancyDao().getVacancyById(id) ?: return null
        return vacancyDbConverter.convert(entity)
    }

    override suspend fun addVacancy(vacancy: Vacancy) {
        appDatabase.vacancyDao().addVacancy(vacancyDbConverter.convert(vacancy))
    }

    override suspend fun removeVacancy(id: String) {
        appDatabase.vacancyDao().removeVacancy(id)
    }
}
