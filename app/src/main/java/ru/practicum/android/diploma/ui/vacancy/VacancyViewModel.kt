package ru.practicum.android.diploma.ui.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.favorites.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.vacancy.VacancyInteractor
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.vacancy.ExternalNavigator
import ru.practicum.android.diploma.ui.model.ScreenState
import ru.practicum.android.diploma.util.Resource

class VacancyViewModel(
    private val vacancyInteractor: VacancyInteractor,
    private val externalNavigator: ExternalNavigator,
    private val favoritesInteractor: FavoritesInteractor
) : ViewModel() {

    private var vacancy: Vacancy? = null

    private val _vacancyState = MutableLiveData<ScreenState<Vacancy>>()
    val vacancyState: LiveData<ScreenState<Vacancy>> = _vacancyState

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    init {
        viewModelScope.launch {
            _vacancyState.postValue(ScreenState.Loading())
        }
    }

    private fun renderFavorite(favorite: Boolean) {
        _isFavorite.postValue(favorite)
    }

    fun changeFavourite() {
        viewModelScope.launch {
            val favorite = _isFavorite.value ?: false
            if (vacancy != null) {
                if (favorite) {
                    favoritesInteractor.removeVacancy(vacancy!!.id)
                } else {
                    favoritesInteractor.addVacancy(vacancy!!)
                }
                renderFavorite(!favorite)
            }
        }
    }

    fun checkFavorite() {
        viewModelScope.launch {
            if (vacancy != null) {
                favoritesInteractor.checkVacancy(vacancy!!.id).collect {
                    renderFavorite(it)
                }
            }
        }
    }

    fun getVacancyState(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = vacancyInteractor.getVacancy(id).single()) {
                is Resource.Success -> _vacancyState.postValue(ScreenState.Loaded(result.data))
                is Resource.Failed -> _vacancyState.postValue(ScreenState.ServerError())
                is Resource.NotConnection -> _vacancyState.postValue(ScreenState.NotConnection())
            }
        }
    }

    fun shareVacation(url: String) {
        externalNavigator.shareVacation(BASE_URL + url)
    }

    companion object {
        const val BASE_URL = "https://hh.ru/vacancy/"
    }
}
