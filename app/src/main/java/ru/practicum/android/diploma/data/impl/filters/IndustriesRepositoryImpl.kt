package ru.practicum.android.diploma.data.impl.filters

import ru.practicum.android.diploma.data.converters.IndustriesConverter
import ru.practicum.android.diploma.data.dto.IndustryDto
import ru.practicum.android.diploma.data.dto.ResponseCode
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.request.IndustriesRequest
import ru.practicum.android.diploma.data.response.ListResponse
import ru.practicum.android.diploma.domain.api.filters.IndustriesRepository
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.Resource

class IndustriesRepositoryImpl(
    val networkClient: NetworkClient,
    val converter: IndustriesConverter
) :
    IndustriesRepository {
    override suspend fun get(): Resource<List<Industry>> {
        val response = networkClient.doRequest(IndustriesRequest) as ListResponse<*>
        return when (response.resultCode) {
            ResponseCode.SUCCESS -> Resource.Success(response.list.map { converter.convert(it as IndustryDto) })
            ResponseCode.NOT_CONNECTION -> Resource.NotConnection()
            else -> Resource.Failed()
        }
    }
}
