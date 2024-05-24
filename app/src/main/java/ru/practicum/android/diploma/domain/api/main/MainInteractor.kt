package ru.practicum.android.diploma.domain.api.main

import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.util.Resource

interface MainInteractor {
    suspend fun searchVacancies(vacancy: String, page: Int): Resource<Vacancies>
}
