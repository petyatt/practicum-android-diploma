package ru.practicum.android.diploma.data.impl.vacancy

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.network.response.VacancyResponse
import ru.practicum.android.diploma.domain.api.HeadHunterVacanciesRepository
import ru.practicum.android.diploma.domain.models.VacancyDetail
import ru.practicum.android.diploma.util.Resource

class HeadHunterVacanciesRepositoryImpl : HeadHunterVacanciesRepository {
    override fun getVacancies(
        query: String?,
        page: Int,
        perPage: Int,
        salary: Int?,
        area: Int?,
        industry: String?,
        onlyWithSalary: Boolean
    ): Flow<Resource<VacancyResponse>> {
        TODO("Not yet implemented")
    }

    override fun getVacancy(id: String): Flow<Resource<VacancyDetail>> {
        // todo - mock-реализация
        return flow {
            Resource.Success(
                VacancyDetail(
                    id = "1",
                    name = "Android-разработчик",
                    salaryMin = 100_000,
                    salaryMax = null,
                    currency = "₽",
                    companyName = "Еда",
                    companyAddress = "Москва",
                    uri = "",
                    experience = "От 1 года до 3 лет",
                    employment = "Полная занятость, Удаленная работа",
                    description = "<b>Описание вакансии</b>",
                    skills = listOf("Скил 1", "Скил 2"),
                    contactName = "Ирина",
                    contactEmail = "",
                    contactPhone = "",
                    contactComment = ""
                )
            )
        }
    }
}
