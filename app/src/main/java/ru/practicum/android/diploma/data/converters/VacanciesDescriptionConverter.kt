package ru.practicum.android.diploma.data.converters

import ru.practicum.android.diploma.data.dto.AddressDto
import ru.practicum.android.diploma.data.dto.AreaDto
import ru.practicum.android.diploma.data.dto.ContactsDto
import ru.practicum.android.diploma.data.dto.EmployerDto
import ru.practicum.android.diploma.data.dto.EmploymentDto
import ru.practicum.android.diploma.data.dto.ExperienceDto
import ru.practicum.android.diploma.data.dto.KeySkillDto
import ru.practicum.android.diploma.data.dto.LogoUrlsDto
import ru.practicum.android.diploma.data.dto.PhoneDto
import ru.practicum.android.diploma.data.dto.SalaryDto
import ru.practicum.android.diploma.data.dto.ScheduleDto
import ru.practicum.android.diploma.data.dto.VacancyDescriptionResponse
import ru.practicum.android.diploma.domain.models.Address
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Contacts
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Employment
import ru.practicum.android.diploma.domain.models.Experience
import ru.practicum.android.diploma.domain.models.KeySkill
import ru.practicum.android.diploma.domain.models.LogoUrls
import ru.practicum.android.diploma.domain.models.Phone
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Schedule
import ru.practicum.android.diploma.domain.models.VacancyDescription

class VacanciesDescriptionConverter {

    fun convertVacancyDescription(response: VacancyDescriptionResponse): VacancyDescription {
        return VacancyDescription(
            id = response.id,
            name = response.name,
            salary = convertSalaryResponse(response.salary),
            employer = convertEmployerResponse(response.employer),
            area = convertAreaResponse(response.area),
            url = response.url,
            address = convertAddressResponse(response.address),
            experience = convertExperienceResponse(response.experience),
            employment = convertEmploymentResponse(response.employment),
            schedule = convertScheduleResponse(response.schedule),
            keySkills = response.keySkills.map { convertKeySkillResponse(it) },
            contacts = convertContactsResponse(response.contacts),
            description = response.description
        )
    }

    private fun convertSalaryResponse(response: SalaryDto?): Salary? {
        return if (response != null) {
            Salary(
                currency = response.currency,
                from = response.from,
                gross = response.gross,
                to = response.to
            )
        } else {
            null
        }
    }

    private fun convertEmployerResponse(response: EmployerDto?): Employer? {
        return if (response != null) {
            Employer(
                alternateUrl = response.alternateUrl,
                id = response.id,
                logoUrls = convertLogoUrlsResponse(response.logoUrls),
                name = response.name,
                url = response.url
            )
        } else {
            null
        }
    }

    private fun convertLogoUrlsResponse(response: LogoUrlsDto?): LogoUrls? {
        return if (response != null) {
            LogoUrls(
                original = response.original
            )
        } else {
            null
        }
    }

    private fun convertAreaResponse(response: AreaDto): Area {
        return Area(
            id = response.id,
            name = response.name,
            url = response.url
        )
    }

    private fun convertAddressResponse(response: AddressDto?): Address? {
        return if (response != null) {
            Address(
                building = response.building,
                city = response.city,
                description = response.description,
                lat = response.lat,
                lng = response.lng,
                street = response.street
            )
        } else {
            null
        }
    }

    private fun convertExperienceResponse(response: ExperienceDto?): Experience? {
        return if (response != null) {
            Experience(
                id = response.id,
                name = response.name
            )
        } else {
            null
        }
    }

    private fun convertEmploymentResponse(response: EmploymentDto?): Employment? {
        return if (response != null) {
            Employment(
                id = response.id,
                name = response.name
            )
        } else {
            null
        }
    }

    private fun convertScheduleResponse(response: ScheduleDto?): Schedule? {
        return if (response != null) {
            Schedule(
                id = response.id,
                name = response.name
            )
        } else {
            null
        }
    }

    private fun convertKeySkillResponse(response: KeySkillDto): KeySkill {
        return KeySkill(
            name = response.name
        )
    }

    private fun convertContactsResponse(response: ContactsDto?): Contacts? {
        return if (response != null) {
            Contacts(
                email = response.email,
                name = response.name,
                phones = response.phones?.map { convertPhoneResponse(it) }
            )
        } else {
            null
        }
    }

    private fun convertPhoneResponse(response: PhoneDto): Phone {
        return Phone(
            city = response.city,
            comment = response.comment,
            country = response.country,
            number = response.number
        )
    }
}
