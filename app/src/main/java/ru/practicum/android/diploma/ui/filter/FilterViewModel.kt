package ru.practicum.android.diploma.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.sharedpreferences.SharedPreferencesInteractor
import ru.practicum.android.diploma.domain.models.Filter

class FilterViewModel(
    private val sharedPreferencesInteractor: SharedPreferencesInteractor
) : ViewModel() {
    fun save(filter: Filter?) {
        viewModelScope.launch {
            sharedPreferencesInteractor.save(filter)
        }
    }

    fun get(): Filter? {
        return sharedPreferencesInteractor.get()
    }

    fun clear() {
        viewModelScope.launch {
            sharedPreferencesInteractor.clear()
        }
    }
}
