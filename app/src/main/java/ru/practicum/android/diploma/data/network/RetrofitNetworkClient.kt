package ru.practicum.android.diploma.data.network

import android.content.Context
import ru.practicum.android.diploma.data.dto.Response

class RetrofitNetworkClient(
    private val headHunterApiService: HeadHunterApiService,
    private val context: Context
): NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        TODO("Not yet implemented")
    }
}
