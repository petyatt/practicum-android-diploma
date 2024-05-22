package ru.practicum.android.diploma.data.impl.main

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.dto.Response
import ru.practicum.android.diploma.data.dto.ResponseCode
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.request.MainRequest
import ru.practicum.android.diploma.data.request.Request
import ru.practicum.android.diploma.data.request.VacancyRequest
import ru.practicum.android.diploma.data.response.VacanciesResponse
import ru.practicum.android.diploma.data.response.VacancyResponse
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.util.Resource

class VacanciesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacanciesConverter: VacanciesConverter
) :
    VacanciesRepository {

    override fun searchVacancies(vacancy: String, page: Int): Flow<Resource<Vacancies>> = flow {
        val response = networkClient.doRequest(MainRequest(vacancy = vacancy, page))
        when (response.resultCode) {
            ResponseCode.SUCCESS -> {
                val data = vacanciesConverter.convert(response as VacanciesResponse)
                emit(Resource.Success(data))
            }
            ResponseCode.NOT_CONNECTION -> emit(Resource.NotConnection())
            else -> emit(Resource.Failed())
        }
    }

    override suspend fun getVacancy(id: String): Flow<Resource<VacancyDetail>> = flow {
        val response = networkClient.doRequest(VacancyRequest(id = id))
        when (response.resultCode) {
            ResponseCode.SUCCESS -> {
                val data = vacanciesConverter.convert(response as VacancyResponse)
                emit(Resource.Success(data))
            }
            ResponseCode.NOT_CONNECTION -> emit(Resource.NotConnection())
            else -> emit(Resource.Failed())
        }

    }
}
