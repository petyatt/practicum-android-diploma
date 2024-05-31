package ru.practicum.android.diploma.ui.favorites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.favorites.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesViewModel(private val favoritesInteractor: FavoritesInteractor) : ViewModel() {

    private val _favouriteVacancies = MutableLiveData<List<Vacancy>>()
    val favouriteVacancies: LiveData<List<Vacancy>> = _favouriteVacancies

    fun getVacancies() {
        viewModelScope.launch {
            favoritesInteractor.getAllVacancies().collect { _favouriteVacancies.postValue(it) }
        }
    }
}
