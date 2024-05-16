package ru.practicum.android.diploma.data.network.response

import com.google.gson.annotations.SerializedName
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.VacancyItemDto

data class VacancyResponse(
    val items: List<VacancyItemDto>,
    val found: Int,
    val pages: Int,
    @SerializedName("per_page") val perPage: Int,
    val page: Int,
) : Response()
