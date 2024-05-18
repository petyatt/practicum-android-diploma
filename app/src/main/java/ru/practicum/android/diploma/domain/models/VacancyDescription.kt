package ru.practicum.android.diploma.domain.models

data class VacancyDescription(
    val id: String,
    val name: String,
    val salary: Salary?,
    val employer: Employer?,
    val area: Area,
    val url: String,
    val address: Address?,
    val experience: Experience?,
    val employment: Employment?,
    val schedule: Schedule?,
    val keySkills: List<KeySkill>,
    val contacts: Contacts?,
    val description: String,
)
