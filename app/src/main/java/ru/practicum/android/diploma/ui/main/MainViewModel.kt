package ru.practicum.android.diploma.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.main.MainInteractor
import ru.practicum.android.diploma.util.Resource

class MainViewModel(
    private val mainInteractor: MainInteractor,
) : ViewModel() {
    private val vacancySearchStateLiveData = MutableLiveData<VacancySearchState>()
    private var _page: Int = 0
    fun observeState(): LiveData<VacancySearchState> = vacancySearchStateLiveData
    private fun renderState(state: VacancySearchState) {
        this.vacancySearchStateLiveData.postValue(state)
    }

    fun sendRequest(searchText: String) {
        if (searchText.isNotEmpty()) {
            if (_page != 0) {
                _page += 1
            }
            renderState(VacancySearchState.Loading)
            viewModelScope.launch {
                val result = mainInteractor.searchVacancies(searchText, _page).single()
                when (result) {
                    is Resource.NotConnection -> {
                        // todo - обработка "Нет соединения"
                        VacancySearchState.Error(Placeholder.BAD_CONNECTION, "todo - not connection")
                    }
                    is Resource.ServerError -> {
                        // todo - обработка "Ошибка сервера"
                        VacancySearchState.Error(Placeholder.BAD_CONNECTION, "todo - server error")
                    }
                    is Resource.Success -> {
                        renderState(VacancySearchState.Content(result.data))
                        _page = result.data.page
                    }
                }
            }
        }
    }

    fun setDefaultState() {
        renderState(VacancySearchState.Default)
    }

}
