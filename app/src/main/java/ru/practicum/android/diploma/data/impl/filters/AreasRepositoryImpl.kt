package ru.practicum.android.diploma.data.impl.filters

import ru.practicum.android.diploma.data.converters.AreasConverter
import ru.practicum.android.diploma.data.dto.CountryDto
import ru.practicum.android.diploma.data.impl.ResourceRepository
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.request.AreasRequest
import ru.practicum.android.diploma.data.request.CountriesRequest
import ru.practicum.android.diploma.data.response.AreasResponse
import ru.practicum.android.diploma.data.response.ListResponse
import ru.practicum.android.diploma.domain.api.filters.AreasRepository
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.util.Resource

class AreasRepositoryImpl(
    val networkClient: NetworkClient,
    val converter: AreasConverter
) : AreasRepository, ResourceRepository() {

    override suspend fun getCountries(): Resource<List<Country>> {
        val response = networkClient.doRequest(CountriesRequest) as ListResponse<*>
        return getResource(response, response.list.map { converter.convert(it as CountryDto) })
    }

    override suspend fun getAreas(parentId: String): Resource<List<Area>> {
        val response = networkClient.doRequest(AreasRequest(parentId)) as AreasResponse
        return getResource(response, response.areas.map { converter.convert(it) })
    }
}
