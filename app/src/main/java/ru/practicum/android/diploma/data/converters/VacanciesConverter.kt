package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.ContactsDto
import ru.practicum.android.diploma.data.dto.IdNameDto
import ru.practicum.android.diploma.data.response.AreaResponse
import ru.practicum.android.diploma.data.response.DepartmentResponse
import ru.practicum.android.diploma.data.response.EmployerResponse
import ru.practicum.android.diploma.data.response.LogoUrlsResponse
import ru.practicum.android.diploma.data.response.SalaryResponse
import ru.practicum.android.diploma.data.response.VacanciesResponse
import ru.practicum.android.diploma.data.response.VacancyResponse
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Employment
import ru.practicum.android.diploma.domain.models.LogoUrls
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail

class VacanciesConverter {
    fun convert(dto: VacancyResponse) = with(dto) {
        VacancyDetail(
            id = id,
            name = name,
            salaryMin = salary?.from,
            salaryMax = salary?.to,
            currency = salary?.currency ?: "",
            companyName = employer.name,
            companylogo = employer.logoUrls?.original ?: "",
            companyAddress = "", // todo -,
            experience = experience?.name ?: "",
            description = description ?: "",
            employment = employment?.name ?: "",
            skills = getSkills(keySkills),
            contactName = contacts?.name ?: "",
            contactEmail = contacts?.email ?: "",
            contactPhone = getPhone(contacts),
            contactComment = getComment(contacts)
        )
    }

    private fun getSkills(list: List<IdNameDto>?) = list?.mapNotNull { it.name } ?: emptyList()
    private fun getPhone(contacts: ContactsDto?) = contacts?.phones?.firstOrNull()?.number ?: ""
    private fun getComment(contacts: ContactsDto?) = contacts?.phones?.firstOrNull()?.comment ?: ""

    fun convert(response: VacanciesResponse): Vacancies {
        return with(response) {
            Vacancies(
                found = this.found,
                items = convertItems(this.items),
                page = this.page,
                pages = this.pages,
                perPage = this.perPage
            )
        }
    }

    private fun convertItems(items: List<VacancyResponse>): List<Vacancy> {
        return items.map { vacancy ->
            Vacancy(
                vacancy.id,
                vacancy.name,
                convertArea(vacancy.area),
                convertEmployer(vacancy.employer),
                convertDepartment(vacancy.department),
                convertSalary(vacancy.salary)
            )
        }
    }

    private fun convertArea(area: AreaResponse): Area {
        return Area(
            id = area.id,
            name = area.name,
            url = area.url
        )
    }

    private fun convertEmployer(employer: EmployerResponse): Employer {
        return Employer(
            alternateUrl = employer.alternateUrl,
            id = employer.id,
            logoUrls = convertLogoUrls(employer.logoUrls),
            name = employer.name,
            url = employer.url
        )
    }

    private fun convertLogoUrls(logoUrls: LogoUrlsResponse?): LogoUrls? {
        return logoUrls?.let { LogoUrls(it.original) }
    }

    private fun convertDepartment(department: DepartmentResponse?): Employment? {
        return department?.let {
            Employment(
                id = it.id,
                name = department.name
            )
        }
    }

    private fun convertSalary(salary: SalaryResponse?): Salary? {
        return salary?.let {
            Salary(
                currency = salary.currency,
                from = salary.from,
                gross = salary.gross,
                to = salary.to
            )
        }
    }
}
