package ru.practicum.android.diploma.domain.models

class Vacancies(
    val found: Int,
    val items: List<Vacancy>,
    val page: Int,
    val pages: Int,
    val perPage: Int,
)
