package ru.practicum.android.diploma.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.main.MainInteractor
import ru.practicum.android.diploma.domain.api.sharedpreferences.FilterIneractor
import ru.practicum.android.diploma.domain.models.Filter
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.ui.model.ScreenState
import ru.practicum.android.diploma.util.Resource

class MainViewModel(
    private val mainInteractor: MainInteractor,
    private val filterInteractor: FilterIneractor
) : ViewModel() {

    private val _state = MutableLiveData<ScreenState<Vacancies>>()
    val state: LiveData<ScreenState<Vacancies>> = _state

    fun getVacancies(searchText: String, page: Int = 0) {
        if (searchText.isNotEmpty()) {
            viewModelScope.launch {
                when (val result =
                    mainInteractor.searchVacancies(searchText, page, filterInteractor.get() ?: Filter())) {
                    is Resource.NotConnection -> _state.postValue(ScreenState.NotConnection())
                    is Resource.Failed -> _state.postValue(ScreenState.ServerError())
                    is Resource.Success -> {
                        _state.postValue(ScreenState.Loaded(result.data))
                    }
                }
            }
        }
    }

    fun getFilterState() {
        viewModelScope.launch {
            val filter = filterInteractor.get() ?: Filter()
            _state.postValue(ScreenState.Option(filter))
        }
    }
}
