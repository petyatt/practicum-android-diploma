package ru.practicum.android.diploma.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.main.MainInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.debounce

class MainViewModel(
    private val mainInteractor: MainInteractor,

) : ViewModel() {
    private val vacancySearchStateLiveData = MutableLiveData<VacancySearchState>()
    fun observeState(): LiveData<VacancySearchState> = vacancySearchStateLiveData
    private fun renderState(state: VacancySearchState) {
        this.vacancySearchStateLiveData.postValue(state)
    }
    fun sendRequest(searchText: String) {
        if (searchText.isNotEmpty()) {
            renderState(VacancySearchState.Loading)
            viewModelScope.launch {
                mainInteractor
                    .searchVacancies(searchText)
                    .collect { foundVacancies ->
                        if (foundVacancies.first == null) {
                            renderState(VacancySearchState.Error(Placeholder.BAD_CONNECTION, foundVacancies.second ?: ""))
                        } else {
                            if (foundVacancies.first!!.items.isEmpty()) {
                                renderState(VacancySearchState.Error(Placeholder.NOTHING_FOUND))
                            } else {
                                renderState(VacancySearchState.Content(foundVacancies.first!!))
                            }
                        }
                    }
            }
        }
    }
}
