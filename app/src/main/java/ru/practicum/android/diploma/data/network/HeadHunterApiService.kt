package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.BuildConfig
import ru.practicum.android.diploma.data.response.VacanciesResponse
import ru.practicum.android.diploma.data.response.VacancyResponse

interface HeadHunterApiService {
    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: DW_YP (petya.07@yandex.ru)"
    )
    @GET("/vacancies")
    suspend fun getVacancies(
        @Query("text") vacancy: String,
        @Query("page") page: Int = 0
    ): VacanciesResponse

    @Headers(
        "Authorization: Bearer ${BuildConfig.HH_ACCESS_TOKEN}",
        "HH-User-Agent: DW_YP (petya.07@yandex.ru)"
    )
    @GET("/vacancies/{id}")
    suspend fun getVacancy(@Path("id") id: String): VacancyResponse

}
