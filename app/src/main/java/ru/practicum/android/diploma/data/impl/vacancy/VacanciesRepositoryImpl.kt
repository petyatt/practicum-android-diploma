package ru.practicum.android.diploma.data.impl.vacancy

import ru.practicum.android.diploma.data.converters.VacanciesConverter
import ru.practicum.android.diploma.data.impl.ResourceRepository
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.request.FilterRequest
import ru.practicum.android.diploma.data.request.MainRequest
import ru.practicum.android.diploma.data.request.VacancyRequest
import ru.practicum.android.diploma.data.response.VacanciesResponse
import ru.practicum.android.diploma.data.response.VacancyResponse
import ru.practicum.android.diploma.domain.api.VacanciesRepository
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.util.Resource

class VacanciesRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacanciesConverter: VacanciesConverter
) : VacanciesRepository, ResourceRepository() {

    override suspend fun searchVacancies(vacancy: String, page: Int): Resource<Vacancies> {
        val response = networkClient.doRequest(MainRequest(vacancy, page))
        return if (response is VacanciesResponse) {
            getResource(response, vacanciesConverter.convert(response))
        } else {
            Resource.NotConnection()
        }
    }

    override suspend fun getVacancy(id: String): Resource<Vacancy> {
        val response = networkClient.doRequest(VacancyRequest(id))
        return if (response is VacancyResponse) {
            getResource(response, vacanciesConverter.convert(response))
        } else {
            Resource.NotConnection()
        }
    }

    override suspend fun searchVacanciesWithFilters(
        vacancy: String?,
        page: Int,
        perPage: Int,
        area: Int?,
        searchField: String?,
        industry: String?,
        salary: Int?,
        onlyWithSalary: Boolean
    ): Resource<Vacancies> {
        val response = networkClient.doRequest(FilterRequest(
            vacancy,page,perPage,searchField,area,industry,salary,onlyWithSalary
        ))
        return if (response is VacanciesResponse) {
            getResource(response, vacanciesConverter.convert(response))
        } else {
            Resource.NotConnection()
        }
    }
}
