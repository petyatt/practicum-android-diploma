package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.data.response.VacanciesResponse
import ru.practicum.android.diploma.data.response.VacancyResponse

interface HeadHunterApiService {
    @GET("/vacancies")
    suspend fun getVacancies(@Query("text") vacancy: String, @Query("page") page: Int = 0): VacanciesResponse

    @GET("/vacancies/{id}")
    suspend fun getVacancy(@Path("id") id: String): VacancyResponse

    @GET("/industries")
    suspend fun getIndustries(): List<IndustryDto>
}
