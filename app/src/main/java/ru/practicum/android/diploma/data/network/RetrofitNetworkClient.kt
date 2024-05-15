package ru.practicum.android.diploma.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.network.request.RequestVacancies

class RetrofitNetworkClient(
    private val headHunterApiService: HeadHunterApiService,
    private val context: Context
) : NetworkClient {
    override suspend fun doRequest(dto: Any): Response {

        if (!isConnected()) {
            return Response().apply { resultCode = -1 }
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

                        Response().apply { resultCode = 200 }
                    }

                    else -> {
                        Response().apply { resultCode = 400 }
                    }
                }

            } catch (e: Throwable){
                Response().apply { resultCode = 500 }
            }


        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}
