package ru.practicum.android.diploma.data.network

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.data.dto.ResponseCode
import ru.practicum.android.diploma.data.request.AreasRequest
import ru.practicum.android.diploma.data.request.CountriesRequest
import ru.practicum.android.diploma.data.request.IndustriesRequest
import ru.practicum.android.diploma.data.request.MainRequest
import ru.practicum.android.diploma.data.request.RegionsRequest
import ru.practicum.android.diploma.data.request.Request
import ru.practicum.android.diploma.data.request.VacancyRequest
import ru.practicum.android.diploma.data.response.ListResponse
import ru.practicum.android.diploma.data.response.Response
import ru.practicum.android.diploma.util.isConnected

class RetrofitNetworkClient(
    private val headHunterApiService: HeadHunterApiService,
    private val context: Context
) : NetworkClient {
    override suspend fun doRequest(dto: Request): Response {
        if (!isConnected(context)) {
            return Response().apply { resultCode = ResponseCode.NOT_CONNECTION }
        }

        return withContext(Dispatchers.IO) {
            try {
                val response = when (dto) {
                    is MainRequest -> headHunterApiService.getVacancies(
                        vacancy = dto.vacancy,
                        filter = dto.filter,
                        page = dto.page
                    )
                    is VacancyRequest -> headHunterApiService.getVacancy(id = dto.id)
                    is IndustriesRequest -> ListResponse(headHunterApiService.getIndustries())
                    is CountriesRequest -> ListResponse(headHunterApiService.getCountries())
                    is AreasRequest -> headHunterApiService.getAreas(parentId = dto.parentId)
                    is RegionsRequest -> ListResponse(headHunterApiService.getRegions())
                    else -> Response().apply { resultCode = ResponseCode.FAILED }
                }
                response.apply { resultCode = ResponseCode.SUCCESS }
            } catch (e: HttpException) {
                Log.e("NetworkClientHttpException", e.message.toString(), e)
                Response().apply { resultCode = ResponseCode.FAILED }
            }
        }
    }
}
