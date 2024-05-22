package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.AddressDto
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
            companyLogo = employer.logoUrls?.original ?: "",
            companyAddress = getAddress(address).ifEmpty { area.name },
            experience = experience?.name ?: "",
            description = description ?: "",
            employment = employment?.name ?: "",
            skills = getSkills(keySkills),
            contactName = contacts?.name ?: "",
            contactEmail = contacts?.email ?: "",
            contactPhone = getPhone(contacts),
            contactComment = getComment(contacts),
        )
    }

    private fun getSkills(list: List<IdNameDto>?) = list?.mapNotNull { it.name } ?: emptyList()
    private fun getPhone(contacts: ContactsDto?) = contacts?.phones?.firstOrNull()?.number ?: ""
    private fun getComment(contacts: ContactsDto?) = contacts?.phones?.firstOrNull()?.comment ?: ""

    private fun getAddress(dto: AddressDto?): String {
        return with(dto ?: return "") {
            listOfNotNull(city, street, building).joinToString(", ")
        }
    }

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
        return Area(area.name)
    }

    private fun convertEmployer(employer: EmployerResponse): Employer {
        return Employer(
            logoUrls = convertLogoUrls(employer.logoUrls),
            name = employer.name,
        )
    }

    private fun convertLogoUrls(logoUrls: LogoUrlsResponse?): LogoUrls? {
        return logoUrls?.let { LogoUrls(it.original) }
    }

    private fun convertDepartment(department: DepartmentResponse?): Employment? {
        return department?.let {
            Employment(department.name)
        }
    }

    private fun convertSalary(salary: SalaryResponse?): Salary? {
        return salary?.let {
            Salary(
                currency = salary.currency,
                from = salary.from,
                to = salary.to
            )
        }
    }
}
