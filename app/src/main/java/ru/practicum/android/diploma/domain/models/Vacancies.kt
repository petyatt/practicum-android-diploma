package ru.practicum.android.diploma.domain.models

data class Vacancies(
    val found: Int = 0,
    val items: List<Vacancy>,
    val page: Int = 0
)
