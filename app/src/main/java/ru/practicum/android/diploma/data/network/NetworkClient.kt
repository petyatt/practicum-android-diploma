package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.request.Request
import ru.practicum.android.diploma.data.response.Response

interface NetworkClient {
    suspend fun doRequest(dto: Request): Response
}
