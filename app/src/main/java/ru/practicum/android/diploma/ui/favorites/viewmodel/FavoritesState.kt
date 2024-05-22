package ru.practicum.android.diploma.ui.favorites.viewmodel

import ru.practicum.android.diploma.domain.models.VacancyDetail

sealed interface FavoritesState {

    object Empty : FavoritesState
    data class Content(val data: List<VacancyDetail>) : FavoritesState
}
