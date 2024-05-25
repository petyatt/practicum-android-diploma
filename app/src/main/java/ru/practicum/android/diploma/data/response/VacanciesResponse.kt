package ru.practicum.android.diploma.data.response

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.AddressDto
import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.ContactsDto
import ru.practicum.android.diploma.data.dto.IdNameDto

class VacanciesResponse(
    val found: Int,
    val items: List<VacancyResponse>,
    val page: Int,
    val pages: Int,
    @SerializedName("per_page") val perPage: Int,
) : Response()

data class VacancyResponse(
    val id: String,
    val name: String,
    val area: AreaDto,
    val employer: EmployerResponse,
    val experience: IdNameDto?,
    val description: String?,
    val employment: IdNameDto?,
    val salary: SalaryResponse?,
    val contacts: ContactsDto?,
    val address: AddressDto?,
    @SerializedName("key_skills") val keySkills: List<IdNameDto>?
) : Response()

class EmployerResponse(
    val id: String?,
    @SerializedName("logo_urls") val logoUrls: LogoUrlsResponse?,
    val name: String,
)

class LogoUrlsResponse(
    val original: String
)

class SalaryResponse(
    val currency: String?,
    val from: Int?,
    val to: Int?
)
