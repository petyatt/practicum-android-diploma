package ru.practicum.android.diploma.domain.models

data class Vacancy(
    val id: String,
    val name: String,
    val area: Area,
    val employer: Employer?,
    val department: Employment?,
    val salary: Salary?
)
