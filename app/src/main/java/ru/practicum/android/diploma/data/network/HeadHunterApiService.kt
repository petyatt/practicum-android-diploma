package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.network.response.VacancyResponse
import ru.practicum.android.diploma.data.response.VacanciesResponse

interface HeadHunterApiService {
    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: DW_YP (petya.07@yandex.ru)"
    )
    @GET("vacancies")
    suspend fun getVacancies(
        @Query("text") text: String? = null,
        @Query("page") page: Int = 0,
        @Query("per_page") perPage: Int = 20,
        @Query("area") area: Int? = null,
        @Query("search_field") searchField: String? = "name",
        @Query("industry") industry: String? = null,
        @Query("salary") salary: Int? = null,
        @Query("only_with_salary") onlyWithSalary: Boolean = false

    ): VacanciesResponse

}
