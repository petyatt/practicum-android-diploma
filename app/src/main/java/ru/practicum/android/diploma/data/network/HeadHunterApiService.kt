package ru.practicum.android.diploma.data.network

import retrofit2.http.GET

interface HeadHunterApiService {
    @GET("/vacancies")
    suspend fun getVacancies()
}
