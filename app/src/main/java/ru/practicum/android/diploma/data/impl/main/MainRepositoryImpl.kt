package ru.practicum.android.diploma.data.impl.main

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.dto.ResponseCode
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.request.MainRequest
import ru.practicum.android.diploma.data.response.VacanciesResponse
import ru.practicum.android.diploma.domain.api.main.MainRepository
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.util.Resource

class MainRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacanciesConverter: VacanciesConverter,
    context: Context
) : MainRepository {
    private val badConnection by lazy {
        context.getString(R.string.bad_connection)
    }
    private val serverError by lazy {
        context.getString(R.string.server_error)
    }

    override fun searchVacancies(vacancy: String, page: Int): Flow<Resource<Vacancies>> = flow {
        val response = networkClient.doRequest(MainRequest(vacancy = vacancy))
        when (response.resultCode) {
            ResponseCode.NETWORK_FAILED -> {
                emit(Resource.Error(badConnection))
            }

            ResponseCode.SUCCESS -> {
                with(response as VacanciesResponse) {
                    val data = vacanciesConverter.convert(response)
                    emit(Resource.Success(data))
                }
            }

            else -> emit(Resource.Error(serverError))
        }
    }
}
