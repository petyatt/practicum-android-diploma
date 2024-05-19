package ru.practicum.android.diploma.data.network

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.ResponseCode
import ru.practicum.android.diploma.data.network.request.RequestVacancies
import ru.practicum.android.diploma.data.request.MainRequest
import ru.practicum.android.diploma.util.isConnected
import java.io.IOException

class RetrofitNetworkClient(
    private val headHunterApiService: HeadHunterApiService,
    private val context: Context
) : NetworkClient {
    @Suppress("TooGenericExceptionCaught")
    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected(context)) {
            return Response().apply { resultCode = ResponseCode.NETWORK_FAILED }
        }

        return withContext(Dispatchers.IO) {
            try {
                when (dto) {
                    is RequestVacancies -> {
                        headHunterApiService.getVacancies(
                            text = dto.text,
                            page = dto.page,
                            perPage = dto.perPage,
                            area = dto.area,
                            industry = dto.industry,
                            salary = dto.salary,
                            onlyWithSalary = dto.onlyWithSalary
                        )

                        Response().apply { resultCode = ResponseCode.SUCCESS }
                    }


                    else -> {
                        Response().apply { resultCode = ResponseCode.BAD_ARGUMENT }
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
                Response().apply { resultCode = ResponseCode.NETWORK_FAILED }
            } catch (e: HttpException) {
                e.printStackTrace()
                getHttpExceptionResponse()
            } catch (e: RuntimeException) {
                e.printStackTrace()
                getRuntimeExceptionResponse()
            } catch (e: Exception) {
                e.printStackTrace()
                Response().apply { resultCode = ResponseCode.SERVER_FAILED }
            }

        }
    }

    private suspend fun getHttpExceptionResponse(): Response {
        return Response().apply { resultCode = ResponseCode.SERVER_FAILED }
    }

    private suspend fun getRuntimeExceptionResponse(): Response {
        return Response().apply { resultCode = ResponseCode.BAD_ARGUMENT }
    }
}
