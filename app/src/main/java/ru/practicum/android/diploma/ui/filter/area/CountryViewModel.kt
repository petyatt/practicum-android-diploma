package ru.practicum.android.diploma.ui.filter.area

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.filters.CountriesInteractor
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.ui.model.ScreenState
import ru.practicum.android.diploma.util.Resource

class CountryViewModel(private val interactor: CountriesInteractor) : ViewModel() {
    private val _countriesState = MutableLiveData<ScreenState<List<Area>>>()
    val countriesState: LiveData<ScreenState<List<Area>>> = _countriesState

    fun getCountries(searchString: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            val result = when (val resource = interactor.search(searchString)) {
                is Resource.Success -> ScreenState.Loaded(resource.data)
                else -> ScreenState.ServerError()
            }
            _countriesState.postValue(result)
        }
    }
}
