package ru.practicum.android.diploma.ui.favorites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.favorites.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.CLICK_DEBOUNCE_DELAY

class FavoritesViewModel(private val favoritesInteractor: FavoritesInteractor) : ViewModel() {

    private var isClickAllowed = true

    private val _favouriteVacancies = MutableLiveData<FavoritesState>()
    val favouriteVacancies: LiveData<FavoritesState> = _favouriteVacancies

    fun getVacancies() {
        viewModelScope.launch {
            favoritesInteractor.getAllVacancies().collect(::renderState)
        }
    }

    fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewModelScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }

    private fun renderState(vacancy: List<Vacancy>) {
        if (vacancy.isEmpty()) {
            _favouriteVacancies.postValue(FavoritesState.Empty)
        } else {
            _favouriteVacancies.postValue(FavoritesState.Content(vacancy))
        }
    }
}
