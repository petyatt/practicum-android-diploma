package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.main.MainInteractor
import ru.practicum.android.diploma.domain.api.main.MainRepository
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.util.Resource

class MainInteractorImpl(
    private val mainRepository: MainRepository
) : MainInteractor {

    override fun searchVacancies(vacancy: String, page: Int): Flow<Pair<Vacancies?, String?>> {
        return mainRepository.searchVacancies(vacancy, page).map { result ->
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
