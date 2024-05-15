package ru.practicum.android.diploma.data.dto


data class VacancyItemDto(
    val id: Int,
    val name: String,
    val description: String?,
    val department: IdNameDto?,
    val experience: IdNameDto?,
    val employment: IdNameDto?,
    val area: IdNameDto?,
    val schedule: IdNameDto?
)

