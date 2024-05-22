package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.request.Request

interface NetworkClient {
    suspend fun doRequest(dto: Request): Response
}
