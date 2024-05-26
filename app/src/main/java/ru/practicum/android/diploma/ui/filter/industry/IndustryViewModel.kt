package ru.practicum.android.diploma.ui.filter.industry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.filters.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.model.ScreenState
import ru.practicum.android.diploma.util.Resource

class IndustryViewModel(val interactor: IndustriesInteractor) : ViewModel() {
    private val _industriesState = MutableLiveData<ScreenState<List<Industry>>>()
    val industriesState: LiveData<ScreenState<List<Industry>>> = _industriesState

    fun getIndustries(searchString: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            val result = when (val resource = interactor.search(searchString)) {
                is Resource.Success -> ScreenState.Loaded(resource.data)
                else -> ScreenState.ServerError()
            }
            _industriesState.postValue(result)
        }
    }
}
