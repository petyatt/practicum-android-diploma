package ru.practicum.android.diploma.ui.filter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesInteractor
import ru.practicum.android.diploma.domain.models.Filter

class FilterViewModel(
    private val sharedPreferencesInteractor: SharedPreferencesInteractor
) : ViewModel() {

    private val _filterLiveData = MutableLiveData<Filter>()
    val filterLiveData: LiveData<Filter>
        get() = _filterLiveData

    fun save(filter: Filter?) {
        val currentFilter = sharedPreferencesInteractor.get() ?: Filter()
        currentFilter.industry = filter?.industry
        viewModelScope.launch {
            sharedPreferencesInteractor.save(currentFilter)
        }
    }

    fun get(): Filter? {
        val filter = sharedPreferencesInteractor.get()
        return filter
    }

    fun clear() {
        viewModelScope.launch {
            sharedPreferencesInteractor.clear()
            _filterLiveData.value = Filter()
        }
    }
}
