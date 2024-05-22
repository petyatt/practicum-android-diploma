package ru.practicum.android.diploma.data.converters

import androidx.room.TypeConverter
import org.json.JSONArray
import ru.practicum.android.diploma.data.db.entity.VacancyEntity
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Employer
import ru.practicum.android.diploma.domain.models.Employment
import ru.practicum.android.diploma.domain.models.LogoUrls
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyDbConverter {
    fun convertFromEntity(vacancyEntity: VacancyEntity): Vacancy = Vacancy(
            id = vacancyEntity.id,
            name = vacancyEntity.name,
            salary = Salary(vacancyEntity.currency, vacancyEntity.salaryMin, vacancyEntity.salaryMax),
            employer = Employer(LogoUrls(vacancyEntity.companyLogo), vacancyEntity.companyName),
            area = Area(vacancyEntity.companyAddress),
            employment = Employment(vacancyEntity.employment)
        )

    fun convertToEntity(vacancy: Vacancy): VacancyEntity {
        return VacancyEntity(
            id = vacancy.id,
            name = vacancy.name,
            salaryMin = vacancy.salary?.from,
            salaryMax = vacancy.salary?.to,
            currency = vacancy.salary?.currency ?: "",
            companyName = vacancy.employer?.name ?: "",
            companyAddress = vacancy.area.name,
            companyLogo = vacancy.employer?.logoUrls?.original ?: "",
            experience = "", // todo -
            description = "", // todo -
            employment = vacancy.employment?.name ?: "",
            skills = JSONArray(emptyList<String>()).toString(),
            contactName = "", // todo -
            contactEmail = "", // todo -
            contactPhone = "", // todo -
            contactComment = "" // todo -
        )
    }

    @TypeConverter
    fun convert(listVacancyEntity: List<VacancyEntity>): List<Vacancy> {
        return listVacancyEntity.map { vacancyEntity ->
            convertFromEntity(vacancyEntity)
        }
    }
}
