package ru.practicum.android.diploma.domain.models

import com.google.gson.annotations.SerializedName

data class Vacancies(
    @SerializedName("found") val found: Int,
    @SerializedName("item") val items: List<Vacancy>,
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("per_page") val perPage: Int,
)
