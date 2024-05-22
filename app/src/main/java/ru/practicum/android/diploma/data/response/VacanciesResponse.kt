package ru.practicum.android.diploma.data.response

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.Response

data class VacanciesResponse(
    val found: Int,
    val items: List<VacancyResponse>,
    val page: Int,
    val pages: Int,
    @SerializedName("per_page") val perPage: Int,
) : Response()

data class VacancyResponse(
    val id: String,
    val name: String,
    val area: AreaResponse,
    val employer: EmployerResponse,
    val department: DepartmentResponse?,
    val description: String?,
    val salary: SalaryResponse?
) : Response()

data class AreaResponse(
    val id: String,
    val name: String,
    val url: String
)

data class EmployerResponse(
    @SerializedName("alternate_url") val alternateUrl: String?,
    val id: String?,
    @SerializedName("logo_urls") val logoUrls: LogoUrlsResponse?,
    val name: String,
    val url: String?
)

data class LogoUrlsResponse(
    val original: String
)

data class DepartmentResponse(
    val id: String,
    val name: String
)

data class SalaryResponse(
    val currency: String?,
    val from: Int?,
    val gross: Boolean?,
    val to: Int?
)
