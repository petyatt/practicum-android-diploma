package ru.practicum.android.diploma.data.impl.filters

import ru.practicum.android.diploma.data.converters.AreasConverter
import ru.practicum.android.diploma.data.dto.CountryDto
import ru.practicum.android.diploma.data.dto.ResponseCode
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.request.CountriesRequest
import ru.practicum.android.diploma.data.response.ListResponse
import ru.practicum.android.diploma.domain.api.filters.AreasRepository
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.util.Resource

class AreasRepositoryImpl(
    val networkClient: NetworkClient,
    val converter: AreasConverter
) : AreasRepository {
    override suspend fun getCountries(): Resource<List<Country>> {
        val response = networkClient.doRequest(CountriesRequest) as ListResponse<*>
        return when (response.resultCode) {
            ResponseCode.SUCCESS -> Resource.Success(response.list.map { converter.convert(it as CountryDto) })
            ResponseCode.NOT_CONNECTION -> Resource.NotConnection()
            else -> Resource.Failed()
        }
    }
}
