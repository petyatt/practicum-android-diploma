package ru.practicum.android.diploma.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.main.MainInteractor
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.ui.model.ScreenState
import ru.practicum.android.diploma.util.Resource

class MainViewModel(
    private val mainInteractor: MainInteractor,
) : ViewModel() {

    private val _state = MutableLiveData<ScreenState<Vacancies>>()
    val state: LiveData<ScreenState<Vacancies>> = _state

    private var _page: Int = 0

    fun sendRequest(searchText: String) {
        if (searchText.isNotEmpty()) {
            if (_page != 0) {
                _page += 1
            }
            _state.postValue(ScreenState.Loading())
            viewModelScope.launch {
                val result = mainInteractor.searchVacancies(searchText, _page).single()
                when (result) {
                    is Resource.NotConnection -> _state.postValue(ScreenState.NotConnection())
                    is Resource.ServerError -> _state.postValue(ScreenState.ServerError())
                    is Resource.Success -> {
                        _state.postValue(ScreenState.Loaded(result.data))
                        _page = result.data.page
                    }
                }
            }
        }
    }
}
