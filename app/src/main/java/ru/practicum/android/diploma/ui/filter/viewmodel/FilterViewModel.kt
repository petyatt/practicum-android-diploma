package ru.practicum.android.diploma.ui.filter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.sharedpreferences.FilterIneractor
import ru.practicum.android.diploma.domain.models.Filter

class FilterViewModel(
    private val interactor: FilterIneractor
) : ViewModel() {

    private val _filterLiveData = MutableLiveData<Filter>()
    val filterLiveData: LiveData<Filter>
        get() = _filterLiveData

    fun get() {
        viewModelScope.launch(Dispatchers.IO) {
            _filterLiveData.postValue(interactor.get() ?: Filter())
        }
    }

    fun save(filter: Filter) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.save(filter)
            _filterLiveData.postValue(interactor.get() ?: Filter())
        }
    }

    fun clear() {
        viewModelScope.launch {
            interactor.clear()
            _filterLiveData.postValue(interactor.get() ?: Filter())
        }
    }
}
