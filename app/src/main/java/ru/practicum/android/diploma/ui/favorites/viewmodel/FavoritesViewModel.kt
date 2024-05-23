package ru.practicum.android.diploma.ui.favorites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.favorites.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesViewModel(private val favoritesInteractor: FavoritesInteractor) : ViewModel() {

    private val _favouriteVacancies = MutableLiveData<FavoritesState>()
    val favouriteVacancies: LiveData<FavoritesState> = _favouriteVacancies

    fun getVacancies() {
        viewModelScope.launch {
            favoritesInteractor.getAllVacancies().collect(::renderState)
        }
    }

    private fun renderState(vacancy: List<Vacancy>) {
        if (vacancy.isEmpty()) {
            _favouriteVacancies.postValue(FavoritesState.Empty)
        } else {
            _favouriteVacancies.postValue(FavoritesState.Content(vacancy))
        }
    }
}
