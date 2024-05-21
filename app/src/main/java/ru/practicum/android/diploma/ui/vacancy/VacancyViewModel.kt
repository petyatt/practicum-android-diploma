package ru.practicum.android.diploma.ui.vacancy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.vacancy.VacancyInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.domain.vacancy.ExternalNavigator
import ru.practicum.android.diploma.ui.model.ScreenState
import ru.practicum.android.diploma.util.Resource

class VacancyViewModel(private val vacancyInteractor: VacancyInteractor,
                        private val externalNavigator: ExternalNavigator) : ViewModel() {
    private val _vacancyState = MutableLiveData<ScreenState<VacancyDetail>>()
    val vacancyState: LiveData<ScreenState<VacancyDetail>> = _vacancyState

    init {
        viewModelScope.launch {
            _vacancyState.postValue(ScreenState.Loading())
        }
    }

    fun getVacancyState(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = vacancyInteractor.getVacancy(id).single()
            when (result) {
                is Resource.Success -> _vacancyState.postValue(ScreenState.Loaded(result.data!!))
                is Resource.Error -> {
                    // todo - здесь будет, в зависимости от типа ошибки, передача соответствующего ScreenState
                    _vacancyState.postValue(ScreenState.ServerError())
                }
            }
        }
    }

    fun shareVacation(url: String){
        externalNavigator.shareVacation(BASE_URL + url)
    }

    companion object {
        const val BASE_URL = "https://hh.ru/vacancy/"
    }
}
