package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.network.request.RequestVacancies
import java.io.IOException

class RetrofitNetworkClient(
    private val headHunterApiService: HeadHunterApiService, private val context: Context
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {
        if (!isConnected()) {
            return Response().apply { resultCode = NO_INTERNET_ERROR }
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

                        Response().apply { resultCode = SUCCESS }
                    }

                    else -> {
                        Response().apply { resultCode = CLIENT_ERROR }
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
                Response().apply { resultCode = SERVER_ERROR }
            } catch (e: HttpException) {
                e.printStackTrace()
                Response().apply { resultCode = SERVER_ERROR }
            } catch (e: RuntimeException) {
                e.printStackTrace()
                Response().apply { resultCode = SERVER_ERROR }
            } catch (e: Exception) {
                e.printStackTrace()
                Response().apply { resultCode = SERVER_ERROR }
            }

        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
            }
        }
        return false
    }

    companion object {
        private const val CLIENT_ERROR = 400
        private const val SERVER_ERROR = 500
        private const val NO_INTERNET_ERROR = -1
        private const val SUCCESS = 200
    }
}
