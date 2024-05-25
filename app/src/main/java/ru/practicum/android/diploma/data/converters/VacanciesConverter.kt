package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.AddressDto
import ru.practicum.android.diploma.data.dto.ContactsDto
import ru.practicum.android.diploma.data.dto.IdNameDto
import ru.practicum.android.diploma.data.response.VacanciesResponse
import ru.practicum.android.diploma.data.response.VacancyResponse
import ru.practicum.android.diploma.domain.models.Vacancies
import ru.practicum.android.diploma.domain.models.Vacancy

class VacanciesConverter {

    fun convert(dto: VacancyResponse) = with(dto) {
        Vacancy(
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

    fun convert(response: VacanciesResponse) = with(response) {
        Vacancies(
            found = found,
            items = items.map { convert(it) },
            page = page,
            pages = pages,
            perPage = perPage
        )
    }

    private fun getSkills(list: List<IdNameDto>?) = list?.mapNotNull { it.name } ?: emptyList()
    private fun getPhone(contacts: ContactsDto?) = contacts?.phones?.firstOrNull()?.formatted ?: ""
    private fun getComment(contacts: ContactsDto?) = contacts?.phones?.firstOrNull()?.comment ?: ""

    private fun getAddress(dto: AddressDto?): String {
        return with(dto ?: return "") {
            listOfNotNull(city, street, building).joinToString(", ")
        }
    }

}
