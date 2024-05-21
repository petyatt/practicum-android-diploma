package ru.practicum.android.diploma.domain.api.main

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.util.Resource

interface MainRepository {

    fun searchVacancies(vacancy: String, page: Int): Flow<Resource<Vacancies>>
}
