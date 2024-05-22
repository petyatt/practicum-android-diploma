package ru.practicum.android.diploma.data.converters

import org.json.JSONArray
import ru.practicum.android.diploma.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.domain.models.VacancyDetail

class VacancyDbConverter {
    fun convert(vacancyEntity: VacancyEntity) = with(vacancyEntity) {
        VacancyDetail(
            id = id,
            name = name,
            salaryMin = salaryMin,
            salaryMax = salaryMax,
            currency = currency,
            companyName = companyName,
            companyAddress = companyAddress,
            companyLogo = companyLogo,
            experience = experience,
            description = description,
            employment = employment,
            skills = fromJsonArray(JSONArray(skills)),
            contactName = companyName,
            contactEmail = contactEmail,
            contactPhone = contactPhone,
            contactComment = contactComment
        )
    }

    fun convert(vacancy: VacancyDetail): VacancyEntity = with(vacancy) {
        VacancyEntity(
            id = id,
            name = name,
            salaryMin = salaryMin,
            salaryMax = salaryMax,
            currency = currency,
            companyName = companyName,
            companyAddress = companyAddress,
            companyLogo = companyLogo,
            experience = experience,
            description = description,
            employment = employment,
            skills = JSONArray(skills).toString(),
            contactName = companyName,
            contactEmail = contactEmail,
            contactPhone = contactPhone,
            contactComment = contactComment
        )
    }

    fun convert(listVacancyEntity: List<VacancyEntity>) = listVacancyEntity.map { vacancyEntity ->
        convert(vacancyEntity)
    }

    private fun fromJsonArray(json: JSONArray): List<String> {
        val result = mutableListOf<String>()
        for (i in 1..<json.length()) result.add(json[i].toString())
        return result.toList()
    }
}
