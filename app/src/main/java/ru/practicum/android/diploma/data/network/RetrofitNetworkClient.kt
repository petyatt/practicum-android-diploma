package ru.practicum.android.diploma.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.ResponseCode
import ru.practicum.android.diploma.data.request.MainRequest
import ru.practicum.android.diploma.util.isConnected

class RetrofitNetworkClient(
    private val headHunterApiService: HeadHunterApiService,
    private val context: Context
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected(context)) {
            return Response().apply { resultCode = ResponseCode.NETWORK_FAILED }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = when (dto) {
                    is MainRequest -> headHunterApiService.getVacancies(vacancy = dto.vacancy, page = dto.page)
                    else -> Response().apply { resultCode = ResponseCode.BAD_ARGUMENT }
                }
                response.apply { resultCode = ResponseCode.SUCCESS }
            } catch (e: HttpException) {
                when (e.code()) {
                    ResponseCode.NOT_FOUND -> Response().apply { resultCode = ResponseCode.NOT_FOUND }
                    ResponseCode.BAD_AUTHORIZATION -> Response().apply {
                        resultCode = ResponseCode.BAD_AUTHORIZATION
                    }
                    else -> Response().apply { resultCode = ResponseCode.SERVER_FAILED }
                }
            } catch (e: Throwable) {
                Response().apply { resultCode = ResponseCode.SERVER_FAILED }
            }
        }
    }
}
