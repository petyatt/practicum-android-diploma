package ru.practicum.android.diploma.ui.main

import ru.practicum.android.diploma.domain.models.Vacancies

sealed interface VacancySearchState {
    object Loading: VacancySearchState
    data class Content(val vacancies: Vacancies): VacancySearchState
    data class Error(val placeholder: Placeholder, val errorMessage: String = ""): VacancySearchState
}
