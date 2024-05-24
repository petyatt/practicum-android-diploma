package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.favorites.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.favorites.FavoritesRepository

class FavoritesInteractorImpl(private val repository: FavoritesRepository) : FavoritesInteractor {
    override fun getAllVacancies() = repository.getAllVacancies()
}
