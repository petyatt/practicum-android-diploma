package ru.practicum.android.diploma.ui.filter.industry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.filters.IndustriesInteractor
import ru.practicum.android.diploma.domain.models.Industry

class IndustryViewModel(val interactor: IndustriesInteractor) : ViewModel() {
    private val _industries = MutableLiveData<List<Industry>>()
    val industries: LiveData<List<Industry>> = _industries

    fun getIndustries(searchString: String = "") {
        viewModelScope.launch(Dispatchers.IO) {
            val result = interactor.search(searchString)
            _industries.postValue(result)
        }
    }
}
