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

    override suspend fun getVacancy(id: String): Flow<Resource<VacancyDetail>> {
        // todo - mock-реализация - заменить на настоящую
        return flow {
            emit(
                Resource.Success(
                    VacancyDetail(
                        id = "1",
                        name = "Android-разработчик",
                        salaryMin = 100_000,
                        salaryMax = null,
                        currency = "₽",
                        companyName = "Еда",
                        companyAddress = "Москва",
                        companylogo = "https://hh.ru/employer-logo/289027.png",
                        experience = "От 1 года до 3 лет",
                        employment = "Полная занятость, Удаленная работа",
                        description = "<b>Описание вакансии</b>",
                        skills = listOf("Скил 1", "Скил 2"),
                        contactName = "Ирина",
                        contactEmail = "l.lozgkina@yandex.ru",
                        contactPhone = "+7 (904) 329-27-71",
                        contactComment = "Заполнить анкету по форме можно на нашем свйте"
                    )
                )
            )
        }
    }
}
