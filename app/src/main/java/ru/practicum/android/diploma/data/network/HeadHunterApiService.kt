package ru.practicum.android.diploma.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.CountryDto
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.data.response.AreasResponse
import ru.practicum.android.diploma.data.response.VacanciesResponse
import ru.practicum.android.diploma.data.response.VacancyResponse

interface HeadHunterApiService {
    @GET("/vacancies")
    suspend fun getVacancies(
        @Query("text") vacancy: String,
        @QueryMap filter: Map<String, String>,
        @Query("page") page: Int = 0
    ): VacanciesResponse

    @GET("/vacancies/{id}")
    suspend fun getVacancy(@Path("id") id: String): VacancyResponse

    @GET("/industries")
    suspend fun getIndustries(): List<IndustryDto>

    @GET("/areas/countries")
    suspend fun getCountries(): List<CountryDto>

    @GET("/areas/{parent_id}")
    suspend fun getAreas(@Path("parent_id") parentId: String): AreasResponse

    @GET("/areas")
    suspend fun getRegions(): List<AreasResponse>

}
