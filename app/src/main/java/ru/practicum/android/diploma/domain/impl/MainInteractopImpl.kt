package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.main.MainInteractor
import ru.practicum.android.diploma.domain.api.main.MainReporistory
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.domain.models.VacancyDescription
import ru.practicum.android.diploma.util.Resource

class MainInteractopImpl(
    private val mainReporistory: MainReporistory
) : MainInteractor {

    override fun searchVacancies(vacancy: String): Flow<Pair<Vacancies?, String?>> {
        return mainReporistory.searchVacancies(vacancy).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }

    override fun getVacancyDescription(vacancyId: String): Flow<Pair<VacancyDescription?, String?>> {
        return mainReporistory.getVacancyDescription(vacancyId).map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}
