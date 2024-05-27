package ru.practicum.android.diploma.ui.filter.area

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.filters.RegionsInteractor
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.ui.model.ScreenState
import ru.practicum.android.diploma.util.Resource

class RegionViewModel(private val interactor: RegionsInteractor) : ViewModel() {
    private val _regionsState = MutableLiveData<ScreenState<List<Area>>>()
    val regionsState: LiveData<ScreenState<List<Area>>> = _regionsState

    fun getRegions(countryId: String, searchString: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = when (val resource = interactor.search(countryId, searchString)) {
                is Resource.Success -> ScreenState.Loaded(resource.data)
                else -> ScreenState.ServerError()
            }
            _regionsState.postValue(result)
        }
    }
}
