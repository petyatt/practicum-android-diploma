package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyDescriptionResponse(
    val id: String,
    val name: String,
    val salary: SalaryDto?,
    val employer: EmployerDto?,
    val area: AreaDto,
    @SerializedName("alternate_url") val url: String,
    val address: AddressDto?,
    val experience: ExperienceDto?,
    val employment: EmploymentDto?,
    val schedule: ScheduleDto?,
    @SerializedName("key_skills") val keySkills: List<KeySkillDto>,
    val contacts: ContactsDto?,
    val description: String
) : Response()
